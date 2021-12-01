package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ReviewMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.ImageDao;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.*;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/market/api")
public class MarketRestController {
    private ReadWriteService<Shop, Long> shopService;
    private ShopMapper shopMapper;
    private ReviewService reviewService;
    private ReviewMapper reviewMapper;
    private UserService userService;
    private ImageDao imageDao;
    private ItemService itemService;
    CityService cityService;


    public MarketRestController(ReadWriteService<Shop, Long> shopService, ShopMapper shopMapper, ReviewService reviewService, ItemService itemService, CityService cityService, ReviewMapper reviewMapper, UserService userService, ImageDao imageDao) {
        this.shopService = shopService;
        this.shopMapper = shopMapper;
        this.reviewService = reviewService;
        this.itemService = itemService;
        this.cityService = cityService;
        this.reviewMapper = reviewMapper;
        this.userService = userService;
        this.imageDao = imageDao;
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<ShopDto> getMarketInfo(@PathVariable(value = "id", required = true) Long id) {
        return new ResponseEntity<>(shopService.getByKey(Shop.class, id).map(shopMapper::shopToDto).orElse(new ShopDto()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@RequestBody Review review) {

        List<User> users = userService.getAll(User.class);
        Random random = new Random();
        User user = users.stream().skip(random.nextInt(users.size() - 1)).findFirst().get();

        List<Image> images = imageDao.getAll(Image.class);
        user.setImages(images);
        Date date = new Date();

        review.setDate(date);
        review.setUser(user);
        reviewService.addReview(review);
        return new ResponseEntity<>(reviewMapper.reviewToReviewDto(review), HttpStatus.CREATED);
    }
    @RequestMapping(value = "/editFavorite", method = RequestMethod.PUT)
    public ResponseEntity<ShopDto> editItemFavorite(@RequestBody Shop shop) {

        shopService.update(shop);
        return new ResponseEntity<>(shopMapper.shopToDto(shop), HttpStatus.CREATED);
    }

}
