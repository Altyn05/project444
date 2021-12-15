package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.FavoriteMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.Favorite;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.repository.FavoriteRepository;
import com.amr.project.repository.UserRepository;
import com.amr.project.service.abstracts.ReadWriteService;
import com.amr.project.service.abstracts.ShopService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class FavoriteRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    FavoriteMapper favoriteMapper;
    @Autowired
    ShopService shopService;
    @Autowired(required = false)
    ReadWriteService<Favorite, Long> favoriteService;
    @Autowired
    ShopMapper shopMapper;
    @Autowired
    UserMapper userMapper;

    @Test
    @WithMockUser(username="user", password="user", roles = {"USER"})
    void testFavorite(){
        User user = userRepository.findUserByUsername("user").orElse(null);
        Favorite favorite = new Favorite();
        List<Shop> shops = new ArrayList<>();
        Shop shop = shopService.getAll(Shop.class).get(0);
        shops.add(shop);
        favorite.setShops(shops);
        favorite.setUser(user);
        favoriteRepository.save(favorite);
        List<Favorite> favorites = (List<Favorite>) favoriteRepository.findAll();
        User user2 = null;
        Shop shop1 = null;
        for(Favorite a: favorites){
            user2 = a.getUser();
            List<Shop> list = a.getShops();
            for(Shop b: list){
                shop1 = b;
            }
        }
        ShopDto shopDto = shopMapper.shopToDto(shop);
        ShopDto shopDto1 = shopMapper.shopToDto(shop1);
        UserDto userDto = userMapper.toDto(user);
        UserDto userDto2 = userMapper.toDto(user2);
        assertEquals(userDto.getId(), userDto2.getId(),
                "не совпадает user.id с user.id в БД-Избранное");
        assertEquals(shopDto.getName(), shopDto1.getName(),
                "не совпадает имя магазина с магазином в БД-Избранное");
        assertNotEquals(shop.hashCode(), shop1.hashCode(),
                "совпадает хэш-код магазина с магазином в БД-Избранное");

    }

    @Test
    void updateFavorite(){
        List<Favorite> favorites = (List<Favorite>) favoriteRepository.findAll();
        List<Shop> shops = new ArrayList<>();
        Shop shop = shopService.getAll(Shop.class).get(1);
        shops.add(shop);
        Favorite favorite = null;
        for(Favorite a: favorites){
            favorite = a;
        }
        favorite.setShops(shops);
        favoriteService.update(favorite);
        List<Favorite> favorites2 = (List<Favorite>) favoriteRepository.findAll();
        Shop shop1 = null;

        for(Favorite a: favorites2) {
            List<Shop> list = a.getShops();
            for (Shop b : list) {
                shop1 = b;
            }
        }
        ShopDto shopDto = shopMapper.shopToDto(shop);
        ShopDto shopDto1 = shopMapper.shopToDto(shop1);
        assertEquals(shopDto.getName(), shopDto1.getName(),
                "не совпадает имя магазина с магазином в БД-Избранное после обновления");
    }
}
