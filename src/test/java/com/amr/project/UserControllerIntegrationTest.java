package com.amr.project;

import com.amr.project.model.dto.AddressDto;
import com.amr.project.webapp.controller.UserController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserController uc;
    @Autowired
    UserControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }


    @Test
    public void linkOnWindowRegistration() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("зарегистрироваться")));
    }

    @Test
    public void registerNewUserTest() throws Exception {
        this.mockMvc.perform(post("/signup")
                .param("username", "uuuu")
                .param("password", "uuuu"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserTest1() throws Exception {
        AddressDto addressDto = new AddressDto();
        this.mockMvc.perform(post("/update").contentType(MediaType.APPLICATION_JSON)
                .param("email", "user@mail.ru")
                .param("username", "user")
                .param("age", "53")
                .param("gender", "MALE")
                .param("password", "user")
                .param("phone", "+79999999")
                .param("firstName", "Ivan")
                .param("lastName", "Ivanov")
                .requestAttr("addressDto", addressDto)
                .param("country", "Gvadelupa")
                .param("city", "Omsk")
                .param("cityIndex", "111111")
                .param("street", "Lugovaya")
                .param("house", "20"))
                .andDo(print())
                .andExpect(status().isFound());
    }

}