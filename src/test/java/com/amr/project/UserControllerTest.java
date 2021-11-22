package com.amr.project;

import com.amr.project.converter.AddressMapperImpl;
import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.CountryService;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.webapp.controller.UserController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestPropertySource("/application-test.properties")
class UserControllerTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CountryService countryService;
    @Autowired
    UserController uc;

    @Test
    public void registerNewUserTest1() {
        UserDto userDto = new UserDto("user", "user");
        User user = userMapper.toModel(userDto);
        userService = mock(UserService.class);
        when(userService.createNewUser(user)).thenReturn(false);
        uc = new UserController(userService, countryService, null, null, userMapper,
                new AddressMapperImpl());
        assertEquals("index", uc.createNewUser(userDto).getViewName());
        verify(userService).createNewUser(user);
    }

    @Test
    public void registerNewUserTest2() {
        UserDto userDto = new UserDto("222", "222");
        User user = userMapper.toModel(userDto);
        userService = mock(UserService.class);
        uc = new UserController(userService, countryService, null, null, userMapper,
                new AddressMapperImpl());
        when(userService.createNewUser(user)).thenReturn(true);
        assertEquals("signup", uc.createNewUser(userDto).getViewName());
    }

}