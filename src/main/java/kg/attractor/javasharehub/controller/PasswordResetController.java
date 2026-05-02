package kg.attractor.javasharehub.controller;

import kg.attractor.javasharehub.model.User;
import kg.attractor.javasharehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PasswordResetController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    private final Map<String, String> tokenStorage = new ConcurrentHashMap<>();

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            String token = UUID.randomUUID().toString();
            tokenStorage.put(token, email);

            String resetUrl = "http://localhost:8089/reset-password?token=" + token;
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Сброс пароля в JavaShareHub");
            message.setText("Для сброса пароля перейдите по ссылке:\n" + resetUrl);

            mailSender.send(message);
            log.info("Письмо для сброса пароля отправлено на {}", email);
        }
        model.addAttribute("message", "Сброс пароля отправлен на почту");
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        if (!tokenStorage.containsKey(token)) {
            model.addAttribute("error", "Неверный или устаревший токен сброса пароля.");
            return "login";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String newPassword,
                                       Model model) {
        String email = tokenStorage.get(token);
        if (email != null) {
            User user = userRepository.findByEmail(email).get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            tokenStorage.remove(token);
            log.info("Пароль успешно изменен для {}", email);
            return "redirect:/login?resetSuccess=true";
        }
        return "redirect:/login?error=true";
    }
}