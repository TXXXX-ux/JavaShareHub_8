package kg.attractor.javasharehub.service;

import kg.attractor.javasharehub.dto.FileDto;
import kg.attractor.javasharehub.dto.FileUploadDto;
import kg.attractor.javasharehub.model.User;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FileService {
    void uploadFile(FileUploadDto uploadDto, User uploader);
    void deleteFile(Long fileId, String username);
    Page<FileDto> getPublicFiles(String category, Pageable pageable);
    List<FileDto> getFilesByUser(User user);
    ResponseEntity<Resource> downloadPublicFile(Long id);
    ResponseEntity<Resource> downloadPrivateFile(String uniqueKey);
}