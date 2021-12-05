package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ReviewMapper;
import com.amr.project.dao.abstracts.ImageDao;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.User;
import com.amr.project.repository.ItemRepository;
import com.amr.project.repository.UserRepository;
import com.amr.project.service.abstracts.ReviewService;
import com.amr.project.util.ImgUtilFromUrl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class ReviewRestControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    ReviewService reviewService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ImageDao imageDao;
    @Autowired
    ReviewMapper reviewMapper;


    @Test
    void test1AddReview() throws Exception {
        Review review = new Review();
        User user = userRepository.findUserByUsername("root228").orElse(null);
        List<Image> images = imageDao.getAll(Image.class);
        user.setImages(images);
        Date date = new Date();
        List<Item> items = new ArrayList<>();
        items.add(itemRepository.findAll().get(0));
        Item item = null;
        for(Item a: items){
            item = a;
        }
        byte[] picture = ImgUtilFromUrl.toByteArray("https://thispersondoesnotexist.com/image");
        review.setUser(user);
        review.setItem(item);
        review.setDate(date);
        review.setDignity("test");
        review.setFlaw("test");
        review.setRating(3);
        review.setText("test");
        review.setPicture(picture);
        reviewService.addReview(review);
        Review review1 = reviewService.findById(16L);
        ReviewDto reviewDto = reviewMapper.reviewToReviewDto(review1);
        String text =  reviewDto.getText();
        Integer rating = reviewDto.getRating();
        mvc.perform(get("/review/16").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
               .andExpect(jsonPath("$.text").value(text))
               .andExpect(jsonPath("$.rating").value(rating));
    }

    @Test
    void test2RemoveReview() throws Exception {
        reviewService.deleteByKeyCascadeIgnore(Review.class, 16L);
        mvc.perform(get("/review").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(15));
    }
}
