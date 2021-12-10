package com.amr.project.webapp.rest_controller;

import com.amr.project.dao.abstracts.CategoryDao;
import com.amr.project.model.entity.Category;
import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;
import com.amr.project.repository.ImageRepository;
import com.amr.project.repository.UserRepository;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.util.ImgUtilFromUrl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemRestControllerIntegrationTest {

    @Autowired
    ItemService itemService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    MockMvc mockMvc;


    @BeforeAll
    public static void setUp() {
    }

    @Test
    @Transactional()
    @WithMockUser(username = "konstantin19", password = "8849")
    public void getItemTest() throws Exception {

        Item testItem = new Item();
        User user = userRepository.findUserByUsername("konstantin19").orElse(null);
        Image image1 = new Image("https://thispersondoesnotexist.com",
                ImgUtilFromUrl.toByteArray("https://thispersondoesnotexist.com"), true );
        Image image2 = new Image("https://thispersondoesnotexist.com",
                ImgUtilFromUrl.toByteArray("https://thispersondoesnotexist.com"), false );
        List<Image> imageList = new ArrayList<>();
        imageList.add(image1);
        imageList.add(image2);
        List<Category> categories = categoryDao.getAll(Category.class).stream().limit(3).collect(Collectors.toList());
        testItem.setUser(user);
        testItem.setName("TestItemName");
        testItem.setDescription("TestItemDescription");
        testItem.setFavorite(false);
        testItem.setPrice(new BigDecimal(334));
        testItem.setImages(imageList);
        testItem.setCategories(categories);
        testItem.setModerated(false);
        testItem.setModerateAccept(false);
        System.out.println(testItem);
        try {
            itemService.delete(itemService.findByName(testItem.getName()));
        } catch (Exception e) {
            itemService.persist(testItem);
        }
        if (itemService.findByName("TestItemName") != null) {
            System.err.println("Тестовый Item сохранен в БД");
        }
        Item itemFetch = itemService.findByName("TestItemName");
        System.out.println("TestItemId in DB: " + itemFetch.getId());
        Long itemId = itemFetch.getId();
        String testItemName = testItem.getName();
        String testItemDescription = testItem.getDescription();
        BigDecimal testItemPrice = itemFetch.getPrice();
        boolean testFavor = testItem.isFavorite();
        boolean moderate = testItem.isModerated();
        boolean moderateAccept = testItem.isModerateAccept();
        Long testItemImage1 = testItem.getImages().get(0).getId();
        Long testItemImage2 = testItem.getImages().get(1).getId();

        mockMvc.perform(get("/api/items/"+ itemId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testItemName))
                .andExpect(jsonPath("$.description").value(testItemDescription))
                .andExpect(jsonPath("$.price").value(testItemPrice))
                .andExpect(jsonPath("$.categories[0]").value(categories.get(0)))
                .andExpect(jsonPath("$.categories[1]").value(categories.get(1)))
                .andExpect(jsonPath("$.favorite").value(testFavor))
                .andExpect(jsonPath("$.moderated").value(moderate))
                .andExpect(jsonPath("$.moderateAccept").value(moderateAccept))
                .andExpect(jsonPath("$.images[0].id").value(testItemImage1))
                .andExpect(jsonPath("$.images[1].id").value(testItemImage2));

        itemService.delete(testItem);
        System.err.println("Тестовый Item удален из БД. Тест прошел успешно.");
    }


}