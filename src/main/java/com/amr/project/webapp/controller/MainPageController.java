package com.amr.project.webapp.controller;

import com.amr.project.model.dto.CategoryDto;
import com.amr.project.service.abstracts.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainPageController {
    private final CategoryService categoryService;

    @GetMapping
    public String showMainPage(Model model) {
        List<CategoryDto> list = categoryService.getAll();
        model.addAttribute("categories", list);
        return "index";
    }
}
