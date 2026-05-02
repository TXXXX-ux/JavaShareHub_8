package kg.attractor.javasharehub.controller;

import jakarta.validation.Valid;
import kg.attractor.javasharehub.dto.UserRegistrationDto;
import kg.attractor.javasharehub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        if (!model.containsAttribute("userDto")) {
            model.addAttribute("userDto", new UserRegistrationDto());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid UserRegistrationDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", userDto);
            return "register";
        }

        userService.registerUser(userDto);
        return "redirect:/login";
    }
}