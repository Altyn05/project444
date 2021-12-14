package com.amr.project.webapp.controller;

import com.amr.project.converter.UserMapper;
import com.amr.project.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public ChatController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public String getMainChatPage(Model model, Principal principal){

        model.addAttribute("user",
                userMapper.toDto(userService.findUserByUsername(principal.getName())));
        return "chat";
    }
}
