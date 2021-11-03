package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.*;
import com.amr.project.repository.UserRepository;
import com.amr.project.service.abstracts.ReadWriteService;
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
    private final ReadWriteService<User,Long> rwUser;
    private final ReadWriteService<Image,Long> rwImage;
    private final UserRepository userRepository;
    private final UserProfileUtil userProfileUtil;

    @GetMapping("/users/principal")
    public Object getUserPrincipal() {
        User user = rwUser.getByKey (User.class,
                ((User)SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal()).getId()).orElse(null);
        return userMapper.toDto(user);
    }

    @PutMapping("/users")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        User user = userMapper.toModel(userDto);
        rwUser.update(userProfileUtil.prepareUser(user));
        return userMapper.toDto(user);
    }
}



