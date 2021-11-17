package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import com.amr.project.repository.UserRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserPageRestControllerInegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;


    @Test
    @WithMockUser(username="user", password="user", roles = {"USER"})
    void getUserPrincipal() throws Exception {
        User user = userRepository.findUserByUsername("user").orElse(null);
        mvc.perform(get("/api/users/principal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.password").value("user"))
                .andExpect(jsonPath("$.password").value(user.getPassword()));

    }

    @Test
    void updateUser() throws Exception {
        UserDto userDto = userMapper.toDto(userRepository.findAll().get(0));
        userDto.setFirstName("test_name");
        ObjectMapper mapper = new ObjectMapper();
        String requestJson=mapper.writeValueAsString(userDto);

        mvc.perform(put("/api/users").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.firstName").value("test_name"));
    }
}