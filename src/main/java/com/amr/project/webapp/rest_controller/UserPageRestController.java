package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.CategoryDto;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.*;
import com.amr.project.service.abstracts.CategoryService;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.util.UserProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserPageRestController {

    private final UserMapper userMapper;
    private final UserProfileUtil userProfileUtil;
    private final UserService userService;
    private final ShopService shopService;
    private final CategoryService categoryService;
    private final ItemService itemService;

    @GetMapping("/users/principal")
    public UserDto getUserPrincipal() {
        return userMapper.toDto(userService.findUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @PutMapping("/users")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userProfileUtil.prepareUser(userDto);
    }


    @GetMapping("/categories")
    public List<CategoryDto> showListCategories() {
        return userService.show().getCategories();
    }

    @PostMapping("/items")
    public Item saveItem(@RequestBody Item item) {
        System.out.println("Вход в рест: " + item);
        User user = userService.findUserByUsername(item.getUser().getUsername());
        item.getUser().setId(user.getId());
        List<Category> categories = new ArrayList<>();
        for (Category category : item.getCategories()) {
            categories.add(categoryService.findByName(category.getName()));
        }
        item.setCategories(categories);
        System.out.println(item);
        itemService.persist(item);
        return item;
    }
}
