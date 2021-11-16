package com.amr.project.webapp.controller;

import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class UserPageController {
    private final ShopService shopService;
    private final UserService userService;

    public UserPageController(ShopService shopService, UserService userService) {
        this.shopService = shopService;
        this.userService = userService;
    }
@GetMapping("/user")
public ModelAndView userPage(Principal principal) {
    User user = userService.findUserByUsername(principal.getName());
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("user");
    modelAndView.addObject("user", user);
    List<Shop> shops = user.getShops();
    modelAndView.addObject("shops", shops);
    return modelAndView; }

}
