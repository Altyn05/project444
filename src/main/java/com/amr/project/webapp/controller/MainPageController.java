package com.amr.project.webapp.controller;

import com.amr.project.service.abstracts.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainPageController {
    private final CategoryService categoryService;

    @GetMapping
    public String showMainPage(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "index";
    }
}
