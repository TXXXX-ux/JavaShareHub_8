package kg.attractor.javasharehub.controller;

import kg.attractor.javasharehub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        // В будущем добавим сюда: model.addAttribute("files", fileService.getAllFiles());
        return "admin"; // Имя нашего будущего HTML-файла
    }

    @PostMapping("/user/toggle/{id}")
    public String toggleUser(@PathVariable Long id) {
        userService.toggleUserStatus(id);
        return "redirect:/admin"; // Возвращаемся обратно в админку
    }
}