package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.CategoryDto;
import com.amr.project.model.dto.UserDto;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.util.UserProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserPageRestController {

    private final UserMapper userMapper;
    private final UserProfileUtil userProfileUtil;
    private final UserService userService;


    @GetMapping("/users/principal")
    public UserDto getUserPrincipal() {
        return userMapper.toDto(userService.findUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @PutMapping("/users")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userProfileUtil.prepareUser(userDto);
    }


    @GetMapping("/categories")
    public List<CategoryDto> showListCategories() {
        return userService.show().getCategories();
    }


}
