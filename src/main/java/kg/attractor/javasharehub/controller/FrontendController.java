package kg.attractor.javasharehub.controller;

import kg.attractor.javasharehub.dto.FileDto;
import kg.attractor.javasharehub.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class FrontendController {

    private final FileService fileService;

    @GetMapping("/")
    public String index(
            @RequestParam(required = false) String category,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        Page<FileDto> filesPage = fileService.getPublicFiles(category, pageable);

        model.addAttribute("files", filesPage.getContent());
        model.addAttribute("currentPage", filesPage.getNumber());
        model.addAttribute("totalPages", filesPage.getTotalPages());
        model.addAttribute("category", category);

        return "index";
    }
}