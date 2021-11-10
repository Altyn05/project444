package com.amr.project;

import com.amr.project.webapp.controller.AdminController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("kiril60")
public class AdminTest {

    private AdminController adminController;

    private MockMvc mockMvc;

    @Autowired
    public AdminTest(AdminController adminController, MockMvc mockMvc) {
        this.adminController = adminController;
        this.mockMvc = mockMvc;
    }

    @Test
    public void tupTest() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("адреса")));
    }

//    @Test
//    public void accessDeniedTest() throws Exception {
//        this.mockMvc.perform(get("/admin"))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("http://localhost/"));
//    }

//    @Test
//    public void correctLoginTest() throws Exception {
//        this.mockMvc.perform(formLogin().user("kiril60").password("7529"))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/admin"));
//    }
//
//    @Test
//    public void correctLoginTest() throws Exception {
//        this.mockMvc.perform(post("/").param("user","kiril60").param("password", "7529"))
//                .andDo(print())
//                 .andExpect(status().isOk());
//    }

}
