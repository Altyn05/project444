package com.amr.project.webapp.rest_controller;


import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.CategoryDao;
import com.amr.project.model.dto.CityDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.repository.CityRepository;
import com.amr.project.repository.ShopRepository;
import com.amr.project.repository.UserRepository;
import com.amr.project.service.abstracts.CityService;
import com.amr.project.service.abstracts.CountryService;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.util.ImgUtilFromUrl;
import com.amr.project.webapp.controller.NewShopController;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@SpringBootTest
public class NewShopControllerUnitTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    NewShopController newShopController;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    ShopService shopService;
    @Autowired
    CountryService countryService;
    @Autowired
    CityService cityService;
    @Autowired
    UserPageRestController userPageRestController;
    @Autowired
    ShopMapper shopMapper;




    @Test
    @WithMockUser(username = "konstantin19", password = "8849")
    public void RESTGetTest() throws IOException {
        Shop testShop = new Shop();
        User user = userRepository.findUserByUsername("konstantin19").orElse(null);
        testShop.setUser(user);
        testShop.setName("UnitTestShopName");
        testShop.setDescription("UnitTestShopDescription");
        testShop.setFavorite(false);
        testShop.setModerated(false);
        testShop.setLogo(new Image("https://thispersondoesnotexist.com",
                ImgUtilFromUrl.toByteArray("https://thispersondoesnotexist.com"), false));
        testShop.setLocation(countryService.findById(1L));
        testShop.setCity(cityService.findById(1L));
        testShop.setPhone("12345");
        shopService.addNewShop(testShop);
        Optional<Shop> shop = shopRepository.findAll().stream().filter(a -> a.getName().equals("UnitTestShopName")).findAny();
        Long id = shop.get().getId();
        String response = newShopController.getOneNew(id);
//        System.out.println("ot rest" + response);
        String name = JsonPath.read(response, "$.name");
        String description = JsonPath.read(response, "$.description");
        String phone = JsonPath.read(response, "$.phone");
        Assertions.assertEquals("UnitTestShopName", name,
                "Не совпадает имя полученное из RESTController и записанное в БД");
        Assertions.assertEquals("UnitTestShopDescription", description,
                "Не совпадает описание полученное из RESTController и записанное в БД");
        Assertions.assertEquals("12345", phone,
                "Не совпадает описание полученное из RESTController и записанное в БД");

        shopRepository.delete(testShop);
        System.out.println("Все тесты прошли успешно");

    }

    @Test
    @WithMockUser(username = "konstantin19", password = "8849")
    public void RESTCreateShopTest() throws IOException {
        Shop testShop = new Shop();
        User user = userRepository.findUserByUsername("konstantin19").orElse(null);
        testShop.setUser(user);
        testShop.setName("UnitTestShopName");
        testShop.setDescription("UnitTestShopDescription");
        testShop.setFavorite(false);
        testShop.setModerated(false);
        testShop.setLogo(new Image("https://thispersondoesnotexist.com",
                ImgUtilFromUrl.toByteArray("https://thispersondoesnotexist.com"), false));
        testShop.setLocation(countryService.findById(1L));
        testShop.setCity(cityService.findById(1L));
        testShop.setPhone("12345");
        Principal  userPrincipal = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(userPrincipal.getName());
        ShopDto shopDto = shopMapper.shopToDto(testShop);
        shopDto.setCityDto(new CityDto("Katmandu"));
//        System.out.println(shopDto);
        newShopController.createNewShop(userPrincipal, shopDto);
        Shop shopfromDB = shopService.findByName("UnitTestShopName");
//        System.out.println("ot DB" + shopfromDB);

        Assertions.assertEquals("UnitTestShopName", shopfromDB.getName(),
                "Не совпадает имя полученное из тестовой модели и записанное в БД");
        Assertions.assertEquals("UnitTestShopDescription", shopfromDB.getDescription(),
                "Не совпадает описание полученное из тестовой модели и записанное в БД");
        Assertions.assertEquals("12345", shopfromDB.getPhone(),
                "Не совпадает описание полученное из RESTController и записанное в БД");

        shopRepository.delete(testShop);
        System.out.println("Все тесты прошли успешно");

    }
}
