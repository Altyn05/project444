package com.amr.project.webapp.controller;


import com.amr.project.model.dto.ShowMainPageDTO;
import com.amr.project.service.abstracts.ShowMainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainPageController {
    private static final int ITEMS_PER_PAGE = 4;
    private static final int SHOPS_PER_PAGE = 6;

    private final ShowMainPageService showMainPageService;

    @Autowired
    public MainPageController(ShowMainPageService showMainPageService) {
        this.showMainPageService = showMainPageService;
    }

    @GetMapping
    public String showMainPage(
            @RequestParam(value = "searchName", required = false) String searchName
            , @RequestParam(value = "itemPage", defaultValue = "1") int itemPage
            , @RequestParam(value = "shopPage", defaultValue = "1") int shopPage
            , Model model
    ) {
        ShowMainPageDTO showMainPageDTO;
        
        if (searchName != null){
            showMainPageDTO = showMainPageService.showSearch(searchName, itemPage, ITEMS_PER_PAGE, shopPage, SHOPS_PER_PAGE);
        } else {
            showMainPageDTO = showMainPageService.show(itemPage, ITEMS_PER_PAGE, shopPage, SHOPS_PER_PAGE);
        }
        model.addAttribute("mainPageDto", showMainPageDTO);
        return "index";
    }

    @GetMapping("/category/{id}")
    public String showMainCategory(
            Model model
            , @PathVariable Long id
            , @RequestParam(value = "itemPage", defaultValue = "1") int itemPage
            , @RequestParam(value = "shopPage", defaultValue = "1") int shopPage
    ) {
       model.addAttribute("mainPageDto", showMainPageService.findItemsByCategory(id, itemPage, ITEMS_PER_PAGE, shopPage, SHOPS_PER_PAGE));
        return "index";
    }
}
