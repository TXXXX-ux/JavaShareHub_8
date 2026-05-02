package kg.attractor.javasharehub.service.impl;

import kg.attractor.javasharehub.dto.FileDto;
import kg.attractor.javasharehub.dto.FileUploadDto;
import kg.attractor.javasharehub.exception.ResourceNotFoundException;
import kg.attractor.javasharehub.model.FileEntity;
import kg.attractor.javasharehub.model.User;
import kg.attractor.javasharehub.repository.FileRepository;
import kg.attractor.javasharehub.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.http.ContentDisposition;
import java.nio.charset.StandardCharsets;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    private final String UPLOAD_DIR = "data/";

    @Override
    public void uploadFile(FileUploadDto dto, User uploader) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis() + "_" + dto.getFile().getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            dto.getFile().transferTo(filePath.toFile());

            String key = dto.isPublic() ? null : UUID.randomUUID().toString();

            FileEntity fileEntity = FileEntity.builder()
                    .name(dto.getFile().getOriginalFilename())
                    .storagePath(filePath.toString())
                    .isPublic(dto.isPublic())
                    .uniqueKey(key)
                    .downloadCount(0)
                    .category(dto.getCategory())
                    .uploader(uploader)
                    .build();

            fileRepository.save(fileEntity);
            log.info("Пользователь {} загрузил файл {}. Публичный: {}", uploader.getEmail(), fileEntity.getName(), dto.isPublic());

        } catch (IOException e) {
            log.error("Ошибка при сохранении файла на диск", e);
            throw new RuntimeException("Не удалось сохранить файл!");
        }
    }

    @Override
    public Page<FileDto> getPublicFiles(String category, Pageable pageable) {
        Page<FileEntity> entities;
        if (category != null && !category.isEmpty()) {
            entities = fileRepository.findAllByIsPublicTrueAndCategory(category, pageable);
        } else {
            entities = fileRepository.findAllByIsPublicTrue(pageable);
        }
        return entities.map(this::toDto);
    }

    @Override
    public List<FileDto> getFilesByUser(User user) {
        return fileRepository.findAllByUploader(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Resource> downloadPublicFile(Long id) {
        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Файл не найден"));

        if (!file.isPublic()) {
            throw new ResourceNotFoundException("Этот файл приватный! Ищите по ключу.");
        }

        file.setDownloadCount(file.getDownloadCount() + 1);
        fileRepository.save(file);
        log.info("Скачан публичный файл: {}", file.getName());

        return createResourceResponse(file);
    }

    @Override
    public ResponseEntity<Resource> downloadPrivateFile(String uniqueKey) {
        FileEntity file = fileRepository.findByUniqueKey(uniqueKey)
                .orElseThrow(() -> {
                    log.warn("Попытка доступа по сгоревшему ключу: {}", uniqueKey);
                    return new ResourceNotFoundException("Ключ сгорел или файл удален!");
                });

        file.setDownloadCount(file.getDownloadCount() + 1);
        file.setUniqueKey(null);

        fileRepository.save(file);
        log.info("Приватный файл {} скачан, ключ уничтожен", file.getName());

        return createResourceResponse(file);
    }

    private ResponseEntity<Resource> createResourceResponse(FileEntity fileEntity) {
        try {
            Path filePath = Paths.get(fileEntity.getStoragePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                ContentDisposition contentDisposition = ContentDisposition.attachment()
                        .filename(fileEntity.getName(), StandardCharsets.UTF_8)
                        .build();

                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                        .body(resource);
            } else {
                throw new ResourceNotFoundException("Файл был утерян");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Ошибка чтения файла", e);
        }
    }

    private FileDto toDto(FileEntity entity) {
        return FileDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .isPublic(entity.isPublic())
                .uniqueKey(entity.getUniqueKey())
                .downloadCount(entity.getDownloadCount())
                .category(entity.getCategory())
                .uploaderEmail(entity.getUploader().getEmail())
                .build();
    }

    @Override
    public void deleteFile(Long fileId, String username) {
        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("Файл не найден"));

        if (!file.getUploader().getEmail().equals(username)) {
            log.warn("Хакерская атака! Пользователь {} пытался удалить чужой файл {}", username, file.getName());
            throw new RuntimeException("У вас нет прав на удаление этого файла!");
        }

        try {
            Path filePath = Paths.get(file.getStoragePath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("Не удалось удалить физический файл с диска: {}", file.getStoragePath(), e);
        }

        fileRepository.delete(file);
        log.info("Файл {} успешно удален владельцем {}", file.getName(), username);
    }
}