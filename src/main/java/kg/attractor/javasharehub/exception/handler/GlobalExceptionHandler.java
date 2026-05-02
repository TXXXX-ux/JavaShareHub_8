package kg.attractor.javasharehub.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.javasharehub.exception.DuplicateEmailException;
import kg.attractor.javasharehub.exception.ResourceNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException ex, HttpServletRequest request, Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("reason", "Упс! Ресурс не найден.");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request.getRequestURI());
        return "error";
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public String handleDuplicateEmail(DuplicateEmailException ex, HttpServletRequest request, Model model) {
        model.addAttribute("status", 400);
        model.addAttribute("reason", "Ошибка регистрации");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request.getRequestURI());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, HttpServletRequest request, Model model) {
        model.addAttribute("status", 500);
        model.addAttribute("reason", "Внутренняя ошибка сервера");
        model.addAttribute("message", "Что-то пошло не так.");
        model.addAttribute("details", request.getRequestURI());
        return "error";
    }
}