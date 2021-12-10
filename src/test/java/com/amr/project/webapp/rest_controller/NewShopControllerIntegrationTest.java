package com.amr.project.webapp.rest_controller;

import com.amr.project.model.dto.CityDto;
import com.amr.project.model.dto.CountryDto;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.repository.ShopRepository;
import com.amr.project.repository.UserRepository;
import com.amr.project.util.ImgUtilFromUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class NewShopControllerIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void userCanCreateShop() throws Exception {
        ShopDto shopDto = new ShopDto();
        shopDto.setName("TestShop");
        userRepository.findUserByUsername("konstantin19").ifPresent(user -> shopDto.setUsername(user.getUsername()));
        shopDto.setDescription("TestDescription");
        shopDto.setPhone("123456");
        shopDto.setModerateAccept(false);
        shopDto.setCityDto(new CityDto("Minsk"));
        shopDto.setEmail("u2k@inbox.ru");
        shopDto.setLocation(new CountryDto("Belarus"));
        shopDto.setFavorite(false);
        shopDto.setLogo(new ImageDto(null, "https://thispersondoesnotexist.com",
                ImgUtilFromUrl.toByteArray("https://thispersondoesnotexist.com"), true));

        mockMvc.perform(post("/user/newShop")
                .content(objectMapper.writeValueAsString(shopDto))
                .with(user("konstantin19").roles("USER").password("8849"))
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        if (shopRepository.findAll().stream().anyMatch(a -> a.getName().equals("TestShop"))){
            System.out.println("Созданный магазин найден в БД");
        }

    }

    @Test
    void userCanDeleteShop() throws Exception {
        Long id = null;
        try {
            id = Objects.requireNonNull(
                    shopRepository.findAll()
                            .stream().filter(a -> a.getName().equals("TestShop"))
                            .findFirst().orElse(null)).getId();
        } catch (NullPointerException e) {
            System.out.println("Shop с таким именем отсутствует в БД");
        }
        System.out.println(id);

        mockMvc.perform(get("/deleteUserShop/" + id)
                .with(user("konstantin19").roles("USER").password("8849"))
                .with(csrf())
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        if (shopRepository.findAll().stream().noneMatch(a -> a.getName().equals("TestShop"))){
            System.out.println("Shop был успешно удален из БД");
        }

    }
}
