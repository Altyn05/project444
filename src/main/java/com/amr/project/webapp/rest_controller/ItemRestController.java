package com.amr.project.webapp.rest_controller;


import com.amr.project.converter.ItemMapper;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Category;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;
import com.amr.project.repository.ItemRepository;
import com.amr.project.service.abstracts.CategoryService;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ItemRestController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ItemService itemService;
    private final ItemMapper itemMapper;


    @GetMapping("/items/{id}")
    public ResponseEntity<ItemDto> getItem(@PathVariable long id)  {
        ItemDto itemDto = itemMapper.toDto(itemService.findById(id));
        return new ResponseEntity<>( itemDto, HttpStatus.OK);
    }

    @PostMapping("/items")
    public ResponseEntity<Item> saveItem(@RequestBody Item item) {
        User user = userService.findUserByUsername(item.getUser().getUsername());
        item.getUser().setId(user.getId());
        List<Category> categories = new ArrayList<>();
        for (Category category : item.getCategories()) {
            categories.add(categoryService.findByName(category.getName()));
        }
        item.setCategories(categories);
        System.out.println(item);
        itemService.persist(item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
