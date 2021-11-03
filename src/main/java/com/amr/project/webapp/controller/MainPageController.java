package com.amr.project.webapp.controller;


import com.amr.project.model.dto.ShowMainPageDTO;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ShowMainPageService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainPageController {

    private final ShowMainPageService showMainPageService;
    private final UserService userService;


    @Autowired
    public MainPageController(ShowMainPageService showMainPageService, UserService userService) {
        this.showMainPageService = showMainPageService;
        this.userService = userService;

    }


    @GetMapping
    public String showMainPage(@RequestParam(value = "searchName", required = false) String searchName, Model model) {
        ShowMainPageDTO showMainPageDTO;
        if (searchName != null){
            showMainPageDTO = showMainPageService.showSearch(searchName);
        } else {
            showMainPageDTO = showMainPageService.show();
        }
        model.addAttribute("mainPageDto", showMainPageDTO);
        return "index";
    }

    @GetMapping("/category/{id}")
    public String showMainCategory(Model model, @PathVariable Long id) {
        model.addAttribute("mainPageDto", showMainPageService.findItemsByCategory(id));
        return "index";
    }

//    @GetMapping("/activate/{code}")
//    public String active(@PathVariable String code){
//        User user = userService.findUserByActivationCode(code);
//        user.setActivate(true);
//        userService.update(user);
//        return "redirect:/";
//    }
}
