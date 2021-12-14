package com.amr.project.webapp.rest_controller;


import com.amr.project.converter.ItemMapper;
import com.amr.project.dao.abstracts.CategoryDao;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Category;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;
import com.amr.project.repository.ItemRepository;
import com.amr.project.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class ItemRestControllerUnitTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    ItemRestController itemRestController;
    @Autowired
    ItemMapper itemMapper;

    @Test
    @WithMockUser(username = "konstantin19", password = "8849")
    public void RESTGetTest() {
        Item testItem = new Item();
        User user = userRepository.findUserByUsername("konstantin19").orElse(null);
        List<Category> categories = categoryDao.getAll(Category.class).stream().limit(3).collect(Collectors.toList());
        testItem.setUser(user);
        testItem.setName("UnitTestItemName");
        testItem.setDescription("UnitTestItemDescription");
        testItem.setFavorite(false);
        testItem.setPrice(new BigDecimal("1234"));
        testItem.setCategories(categories);
        testItem.setModerated(false);
        itemRepository.delete(testItem);
        itemRepository.save(testItem);
        Optional<Item> item = itemRepository.findItemByName("UnitTestItemName");
        Long id = item.get().getId();
        ResponseEntity<ItemDto> itemDto = itemRestController.getItem(id);

        Assertions.assertEquals("UnitTestItemName", Objects.requireNonNull(itemDto.getBody()).getName(),
                "Не совпадает имя полученное из RESTController и записанное в БД");
        Assertions.assertEquals("UnitTestItemDescription", itemDto.getBody().getDescription(),
                "Не совпадает описание полученное из RESTController и записанное в БД");
        Assertions.assertFalse(itemDto.getBody().isModerated(), "Новый Item должен быть не модерирован");
        Assertions.assertFalse(itemDto.getBody().isFavorite(), "Не должен быть в избранных");
        Assertions.assertEquals(testItem.getCategories().toString(), itemMapper.toModel(itemDto.getBody()).getCategories().toString(),
                "Не верные категории, не совпадают");
        itemRepository.delete(testItem);
        System.out.println("Все тесты прошли успешно");

    }

    @Test
    public void RESTPostTest() {
        Item testItem = new Item();
        User user = userRepository.findUserByUsername("konstantin19").orElse(null);
        List<Category> categories = categoryDao.getAll(Category.class).stream().limit(3).collect(Collectors.toList());
        testItem.setUser(user);
        testItem.setName("UnitTestItemName");
        testItem.setDescription("UnitTestItemDescription");
        testItem.setFavorite(false);
        testItem.setImages(new ArrayList<>());
        testItem.setReviews(new ArrayList<>());
        testItem.setPrice(new BigDecimal("1234.00"));
        testItem.setCategories(categories);
        testItem.setModerated(false);
        itemRestController.saveItem(testItem);
        Item itemRetreivedfromDB = itemRepository.findItemByName("UnitTestItemName").get();
        Assertions.assertEquals(testItem.toString(), itemRetreivedfromDB.toString(), "Неодинаковые объекты");
    }

}
