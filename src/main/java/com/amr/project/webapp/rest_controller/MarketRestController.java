package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.ImageDao;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ReadWriteService;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.service.impl.ReviewServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/market/api")
public class MarketRestController {
    private ReadWriteService<Shop, Long> shopService;
    private ShopMapper shopMapper;
    private ReviewServiceImpl reviewService;
    private UserService userService;
    private ImageDao imageDao;

    public MarketRestController(ReadWriteService<Shop, Long> shopService, ShopMapper shopMapper, ReviewServiceImpl reviewService, UserService userService, ImageDao imageDao) {
        this.shopService = shopService;
        this.shopMapper = shopMapper;
        this.reviewService = reviewService;
        this.userService = userService;
        this.imageDao = imageDao;
    }

    @GetMapping("/info/{id}")
    public ShopDto getMarketInfo(@PathVariable(value = "id", required = true) Long id) {
        return shopService.getByKey(Shop.class, id).map(shopMapper::shopToDto).orElse(new ShopDto());
    }

    @PostMapping
    public Review addReview(@RequestBody Review review) {

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
