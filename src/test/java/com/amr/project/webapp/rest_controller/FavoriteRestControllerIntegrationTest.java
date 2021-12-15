package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.FavoriteMapper;
import com.amr.project.converter.ItemMapper;
import com.amr.project.model.dto.FavoriteDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Favorite;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;
import com.amr.project.repository.FavoriteRepository;
import com.amr.project.repository.ItemRepository;
import com.amr.project.repository.UserRepository;
import com.amr.project.service.abstracts.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class FavoriteRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    FavoriteMapper favoriteMapper;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;
    @Autowired
    ItemMapper itemMapper;

    @Test
    @WithMockUser(username = "root228", password = "root228", roles = {"ADMIN"})
    void addFavorite() throws Exception {
        User user = userRepository.findUserByUsername("root228").orElse(null);
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        List<Item> items = new ArrayList<>();
        items.add(itemRepository.findAll().get(0));
        Item item = null;
        ItemDto itemDto = itemMapper.toDto(item);

        for (Item a : items) {
            item = a;
            itemDto = itemMapper.toDto(a);
        }
        String name = itemDto.getName();
        item.setFavorite(true);
        favorite.setItems(items);
        favoriteRepository.save(favorite);
        itemService.update(item);
        mvc.perform(get("/favorites/getItemsByFavorites").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0][0].name").value(name))
                .andExpect(jsonPath("$.[0][0].favorite").value(true));
    }

    @Test
    void removeFavorites() throws Exception {
        List<Favorite> favorite = (List<Favorite>) favoriteRepository.findAll();
        FavoriteDto favoriteDto = null;
        for (Favorite a : favorite) {
            favoriteDto = favoriteMapper.toDto(a);
        }
        Long id = favoriteDto.getId();
        favoriteRepository.deleteById(id);
        mvc.perform(get("/favorites/getItemsByFavorites").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

}
