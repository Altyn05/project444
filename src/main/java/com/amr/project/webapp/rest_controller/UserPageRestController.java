package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.*;
import com.amr.project.repository.UserRepository;
import com.amr.project.service.abstracts.ReadWriteService;
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
    private final ReadWriteService<User,Long> rwUser;
    private final UserProfileUtil userProfileUtil;
    private final UserService userService;

    @GetMapping("/users/principal")
    public Object getUserPrincipal() {
        return userMapper.toDto(userService.findUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())
        );
    }

    @PutMapping("/users")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        User user = userMapper.toModel(userDto);
        rwUser.update(userProfileUtil.prepareUser(user));
        return userMapper.toDto(user);
    }
}
