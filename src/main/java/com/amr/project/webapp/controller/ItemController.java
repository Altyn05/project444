package com.amr.project.webapp.controller;

import com.amr.project.dao.abstracts.ImageDao;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ReviewService;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.service.impl.ItemPageService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Controller
public class ItemController {
    private static String UPLOADED_FOLDER = System.getProperty("java.io.tmpdir");
    private final ItemPageService itemPageService;
    private UserService userService;
    private ReviewService reviewService;
    private ImageDao imageDao;

    public ItemController(ItemPageService itemPageService, UserService userService, ReviewService reviewService, ImageDao imageDao) {
        this.itemPageService = itemPageService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.imageDao = imageDao;
    }

    @GetMapping("/item/{id}")
    public String itemPage(Model model, @PathVariable(required = true) Long id) {
        model.addAttribute("item", itemPageService.getItem(id));
        model.addAttribute("categories", itemPageService.getCategories());
        return "item";
    }

    @GetMapping("/item/findAll/{id}")
    @ResponseBody
    public ItemDto getItemDto(@PathVariable("id") Long id){
        return itemPageService.getItem(id);
    }

    @RequestMapping(value = "/item/findAll", method = RequestMethod.POST)
    @ResponseBody
    public Review addReview(@RequestBody Review review) throws IOException {

        List<User> users = userService.getAll(User.class);
        Random random = new Random();
        User user = users.stream().skip(random.nextInt(users.size() - 1)).findFirst().get();

        List<Image> images = imageDao.getAll(Image.class);
        user.setImages(images);
        Date date = new Date();

        review.setDate(date);
        review.setUser(user);
        reviewService.addReview(review);
        return review;
    }
}
