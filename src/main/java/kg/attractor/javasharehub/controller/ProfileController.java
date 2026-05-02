package kg.attractor.javasharehub.controller;

import kg.attractor.javasharehub.dto.FileUploadDto;
import kg.attractor.javasharehub.model.User;
import kg.attractor.javasharehub.service.FileService;
import kg.attractor.javasharehub.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final FileService fileService;

    @GetMapping
    public String profilePage(Principal principal, Model model) {
        User currentUser = userService.findByEmail(principal.getName());

        model.addAttribute("user", currentUser);
        model.addAttribute("myFiles", fileService.getFilesByUser(currentUser));

        return "profile";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("category") String category,
                             @RequestParam(value = "isPublic", defaultValue = "false") boolean isPublic,
                             Principal principal) {

        User currentUser = userService.findByEmail(principal.getName());

        FileUploadDto uploadDto = new FileUploadDto();
        uploadDto.setFile(file);
        uploadDto.setCategory(category);
        uploadDto.setPublic(isPublic);

        fileService.uploadFile(uploadDto, currentUser);

        return "redirect:/profile";
    }

    @PostMapping("/delete/{id}")
    public String deleteFile(@PathVariable("id") Long id, Principal principal) {
        fileService.deleteFile(id, principal.getName());
        return "redirect:/profile";
    }
}