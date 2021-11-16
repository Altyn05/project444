package com.amr.project;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

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

    private final MockMvc mockMvc;

    @Autowired
    public AdminTestController(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void successLoginTest() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(authenticated());
    }

    @Test
    public void principalTest() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Приветствую администратор: Baranov Alex")));
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
                .andExpect(jsonPath("$.name").value("Belarus"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
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
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("NeniRussia"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    void createCityTest() throws Exception {
        this.mockMvc.perform(post("http://localhost:8888/adminapi/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {" +
                                "    \"name\": \"City\"," +
                                "    \"country\": {" +
                                "      \"name\": \"1\"" +
                                "    }" +
                                "  }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.name").value("City"))
                .andExpect(jsonPath("$.country.id").value(1))
                .andExpect(jsonPath("$.country.name").value("Ukraine"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateCityTest() throws Exception {
        this.mockMvc.perform(patch("http://localhost:8888/adminapi/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(" {" +
                                "\"id\": 2," +
                                "    \"name\": \"update\"," +
                                "    \"country\": {" +
                                "      \"name\": \"2\"" +
                                "    }" +
                                "  }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("update"))
                .andExpect(jsonPath("$.country.id").value(2))
                .andExpect(jsonPath("$.country.name").value("Russia"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createAddressTest() throws Exception {
        this.mockMvc.perform(post("http://localhost:8888/adminapi/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"cityIndex\": \"125486\"," +
                                "    \"street\": \"Street 427\"," +
                                "    \"house\": \"80\"," +
                                "    \"city\": {" +
                                "      \"name\": 1" +
                                "    }," +
                                "    \"country\": {" +
                                "      \"name\": 1" +
                                "    }" +
                                "  }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.cityIndex").value("125486"))
                .andExpect(jsonPath("$.street").value("Street 427"))
                .andExpect(jsonPath("$.house").value("80"))
                .andExpect(jsonPath("$.city.id").value(1))
                .andExpect(jsonPath("$.city.name").value("Mirgorod"))
                .andExpect(jsonPath("$.country.id").value(1))
                .andExpect(jsonPath("$.country.name").value("Ukraine"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateAddressTest() throws Exception {
        this.mockMvc.perform(patch("http://localhost:8888/adminapi/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"id\": 1," +
                                "    \"cityIndex\": \"125486\"," +
                                "    \"street\": \"Street 427\"," +
                                "    \"house\": \"80\"," +
                                "    \"city\": {" +
                                "      \"name\": 1" +
                                "    }," +
                                "    \"country\": {" +
                                "      \"name\": 1" +
                                "    }" +
                                "  }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cityIndex").value("125486"))
                .andExpect(jsonPath("$.street").value("Street 427"))
                .andExpect(jsonPath("$.house").value("80"))
                .andExpect(jsonPath("$.city.id").value(1))
                .andExpect(jsonPath("$.city.name").value("Mirgorod"))
                .andExpect(jsonPath("$.country.id").value(1))
                .andExpect(jsonPath("$.country.name").value("Ukraine"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


}
