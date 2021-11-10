package com.amr.project;

import com.amr.project.webapp.controller.AdminController;
import lombok.NoArgsConstructor;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
@TestPropertySource("/application-test.properties")
@Sql(value = {"create-user-before.sql", "create-country,city,address-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"create-user-after.sql", "create-country,city,address-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AdminTestController {
    private AdminController adminController;
    private MockMvc mockMvc;

    @Autowired
    public AdminTestController(AdminController adminController, MockMvc mockMvc) {
        this.adminController = adminController;
        this.mockMvc = mockMvc;
    }

    @Test
    void createCountryTest() throws Exception {
        this.mockMvc.perform(post("http://localhost:8888/adminapi/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\": \"Belarus\"" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Belarus"));
    }

    @Test
    void updateCountry() throws Exception {
        this.mockMvc.perform(patch("http://localhost:8888/adminapi/countries/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": 2," +
                                "\"name\": \"NeniRussia\"" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NeniRussia"));
    }



//    @Test
//    public void tupTest() throws Exception {
//        this.mockMvc.perform(get("/admin"))
//                .andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("адреса")));
//    }
//
//    @Test
//    public void successLoginTest() throws Exception {
//        this.mockMvc.perform(get("/admin"))
//                .andDo(print())
//                .andExpect(authenticated());
//    }
}
