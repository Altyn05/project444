package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ImageMapper;
import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Role;
import com.amr.project.model.entity.User;
import com.amr.project.repository.RoleRepository;
import com.amr.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class UserPageRestControllerTest {

    @Autowired
    UserPageRestController userPageRestController;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ImageMapper imageMapper;


    @Test
    @WithMockUser(username="test_user", password="test_password", roles = {"TEST1","TEST2"})
    void mockGetUserPrincipal() {
        User user = new User();
        Role role1 = new Role("TEST1");
        Role role2 = new Role("TEST2");
        roleRepository.save(role1);
        roleRepository.save(role2);
        user.setUsername("test_user");
        user.setPassword("test_password");
        user.setRoles(Set.of(role1,role2));
        userRepository.save(user);

        assertEquals("test_user", userPageRestController.getUserPrincipal().getUsername(),
                "не совпадает логин с БД");
        assertEquals("test_password", userPageRestController.getUserPrincipal().getPassword(),
                "не совпадает пароль с БД");
        assertEquals("test_password", SecurityContextHolder.getContext().getAuthentication().getCredentials(),
                "не совпадают пароль принсипала с БД");
        assertEquals("[ROLE_TEST1, ROLE_TEST2]", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString(),
                "не совпадают роли принсипала с БД");
        assertEquals(userMapper.toDto(userRepository.findUserByUsername(user.getUsername()).orElse(null)),
                userPageRestController.getUserPrincipal(), "не совпадают какие-то поля после конвертации в DTO");

    }

    @Test
    void updateUser() {
        UserDto userDto = userMapper.toDto(userRepository.findUserByUsername("test_user").orElse(null));

        ImageDto imagedto = imageMapper.toDto(new Image("https://thispersondoesnotexist.com/image"));

        userDto.setImages(List.of(imagedto));
        userDto.setEmail("test@test.ru");
        userDto.setPhone("123456789");

        assertEquals(userDto, userPageRestController.updateUser(userDto), "после обновления поля DTO не совпадают");
        assertEquals(userDto, userMapper.toDto(userRepository.findUserByUsername(userDto.getUsername()).orElse(null)),
                "DTO неправильно сохранился в БД");


    }
}