package kg.attractor.javasharehub.controller;

import kg.attractor.javasharehub.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/download")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/public/{id}")
    public ResponseEntity<Resource> downloadPublic(@PathVariable Long id) {
        log.info("Запрос на скачивание публичного файла с ID: {}", id);
        return fileService.downloadPublicFile(id);
    }

    @GetMapping("/private/{key}")
    public ResponseEntity<Resource> downloadPrivate(@PathVariable String key) {
        log.info("Попытка скачивания приватного файла по ключу: {}", key);
        return fileService.downloadPrivateFile(key);
    }
}