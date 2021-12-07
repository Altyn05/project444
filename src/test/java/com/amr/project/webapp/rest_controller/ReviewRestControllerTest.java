package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ReviewMapper;
import com.amr.project.dao.abstracts.ImageDao;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.*;
import com.amr.project.repository.ShopRepository;
import com.amr.project.repository.UserRepository;
import com.amr.project.service.abstracts.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class ReviewRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    ReviewService reviewService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    ImageDao imageDao;
    @Autowired
    ReviewMapper reviewMapper;

    @Test
    void test1AddReview(){
        Review review = new Review();
        User user = userRepository.findUserByUsername("root228").orElse(null);
        List<Image> images = imageDao.getAll(Image.class);
        user.setImages(images);
        Date date = new Date();
        List<Shop> shops = new ArrayList<>();
        shops.add(shopRepository.findAll().get(0));
        Shop shop = null;
        for(Shop a: shops){
            shop = a;
        }
        review.setText("text");
        review.setUser(user);
        review.setShop(shop);
        review.setDate(date);
        review.setDignity("good");
        review.setFlaw("bad");
        review.setRating(3);

        reviewService.addReview(review);
        Review review1 = reviewService.findById(16L);
        ReviewDto reviewDto = reviewMapper.reviewToReviewDto(review1);
        assertEquals(review.getText(), reviewDto.getText(),
                "не совпадает текст отзыва с отзывом в БД-Избранное");
        assertNotEquals(review.getShop(), review1.getShop(),
                "совпадает хэш-код магазина с магазином в БД-Отзывы");
        assertNotEquals(review.getDate(), reviewDto.getDate(),
                "совпадает дата отзыва с отзывом в БД-Отзывы");
    }

    @Test
    void test2UpdateReview(){
        Review review = reviewService.findById(16L);
        List<Shop> shops = new ArrayList<>();
        shops.add(shopRepository.findAll().get(1));
        Shop shop = null;
        for(Shop a: shops){
            shop = a;
        }
        review.setShop(shop);
        review.setRating(3);
        review.setText("privet");
        reviewService.update(review);
        Review review1 = reviewService.findById(16L);
        ReviewDto reviewDto = reviewMapper.reviewToReviewDto(review1);

        assertEquals(review.getShop().getName(), reviewDto.getShopName(),
                "не совпадает имя магазина с магазином в БД-Отзывы после обновления");
        assertEquals(review.getText(), reviewDto.getText(),
                "не совпадает текст отзыва с отзывом в БД-Отзывы после обновления");
        assertEquals(review.getRating(), reviewDto.getRating(),
                "не совпадает рейтинг отзыва с отзывом в БД-Отзывы после обновления");
    }
}
