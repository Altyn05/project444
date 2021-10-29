package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.UserMapper;
import com.amr.project.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserPageRestController {

    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping("/users/principal")
    public Object getUserPrincipal() {
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userMapper.toDto(userService.findUserByUsername(principalName));
    }
}
