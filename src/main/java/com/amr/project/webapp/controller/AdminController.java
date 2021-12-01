package com.amr.project.webapp.controller;

import com.amr.project.converter.AdminUserMapper;
import com.amr.project.converter.UserMapper;
import com.amr.project.service.abstracts.AdminService;
import com.amr.project.service.abstracts.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    private final AdminUserMapper userMapper = Mappers.getMapper(AdminUserMapper.class);

    @Autowired
    public AdminController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model, Principal principal){

        model.addAttribute("user",
                userMapper.userToAdminUserDto(userService.findUserByUsername(principal.getName())));
        model.addAttribute("allYouNeed", adminService.show());
        return "admin/index";
    }

}
