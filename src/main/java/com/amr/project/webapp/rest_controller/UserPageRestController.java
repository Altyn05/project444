package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.*;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.util.UserProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserPageRestController {

    private final UserMapper userMapper;
    private final UserProfileUtil userProfileUtil;
    private final UserService userService;

    @GetMapping("/users/principal")
    public UserDto getUserPrincipal() {
        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(user.getOrders());
        UserDto userDto = userMapper.toDto(user);
        System.out.println(userDto);
        return userMapper.toDto(userService.findUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @PutMapping("/users")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userProfileUtil.prepareUser(userDto);
    }
}
