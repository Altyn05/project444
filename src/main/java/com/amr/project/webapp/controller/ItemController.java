package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemPageMapper;
import com.amr.project.converter.ReviewMapper;
import com.amr.project.dao.abstracts.ImageDao;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ReviewService;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.service.impl.ItemPageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class ItemController {
    private static String UPLOADED_FOLDER = System.getProperty("java.io.tmpdir");
    private final ItemPageService itemPageService;
    private ItemService itemService;
    private UserService userService;
    private ReviewService reviewsService;
    private ReviewMapper reviewMapper;
    private ImageDao imageDao;
    private ItemPageMapper itemMapper;

    public ItemController(ItemPageService itemPageService, ItemService itemService, UserService userService, ReviewService reviewsService, ReviewMapper reviewMapper, ImageDao imageDao, ItemPageMapper itemMapper) {
        this.itemPageService = itemPageService;
        this.itemService = itemService;
        this.userService = userService;
        this.reviewsService = reviewsService;
        this.reviewMapper = reviewMapper;
        this.imageDao = imageDao;
        this.itemMapper = itemMapper;
    }

    @GetMapping("/item/{id}")
    public String itemPage(Model model, @PathVariable(required = true) Long id) {
        model.addAttribute("item", itemPageService.getItem(id));
        model.addAttribute("categories", itemPageService.getCategories());
        return "item";
    }

    @GetMapping("/item/findAll/{id}")
    @ResponseBody
    public ResponseEntity<ItemDto> getItemDto(@PathVariable("id") Long id){
        return  new ResponseEntity<>(itemPageService.getItem(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/item/findAll", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ReviewDto> addReview(@RequestBody Review review) throws IOException {

        List<User> users = userService.getAll(User.class);
        Random random = new Random();
        User user = users.stream().skip(random.nextInt(users.size() - 1)).findFirst().get();

        List<Image> images = imageDao.getAll(Image.class);
        user.setImages(images);
        Date date = new Date();

        review.setDate(date);
        review.setUser(user);
        reviewsService.addReview(review);
        return new ResponseEntity<>(reviewMapper.reviewToReviewDto(review), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/item/findAll/editFavorite", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ItemDto> editItemFavorite(@RequestBody Item item) {

        itemService.update(item);
        return new ResponseEntity<>(itemMapper.itemToItemDto(item), HttpStatus.CREATED);
    }


}
