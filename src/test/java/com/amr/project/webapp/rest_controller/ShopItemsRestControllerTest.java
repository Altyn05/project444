package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.repository.ItemRepository;
import com.amr.project.repository.ShopRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ShopItemsRestControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    ShopMapper shopMapper;

    @Test
    @WithMockUser(username="user", password="user", roles = {"USER"})
    void Items() throws Exception {
        List<ItemDto> items;
        items = itemMapper.toListDto(itemRepository.findAllByShopIsNull().orElse(null));

        mvc.perform(get("/api/shopItems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(items.size()));
    }

    @Test
    void editItem() throws Exception {
        List<Item> listItems = itemRepository.findAll();
        Shop shop = shopRepository.findAll().get(0);
        shop.setItems(new ArrayList<>());
        for (Item item: listItems) {
            item.setShop(null);
            itemRepository.save(item);
            shop.addItem(item);
        }
        shopRepository.save(shop);
        ShopDto shopDto = shopMapper.shopToDto(shop);
        int itemsSize = shopDto.getItems().size();
        String name1 = shopDto.getItems().get(2).getName();
        shopDto.getItems().remove(0);
        shopDto.getItems().remove(0);
        ObjectMapper mapper = new ObjectMapper();
        String requestJson=mapper.writeValueAsString(shopDto);

        mvc.perform(put("/api/shopItems").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.items.length()").value(itemsSize-2))
                .andExpect(jsonPath("$.items.length()").value(shopRepository.getOne(shopDto.getId()).getItems().size()))
                .andExpect(jsonPath("$.items[0].name").value(name1))
                .andExpect(jsonPath("$.items[0].name").value(shopRepository.getOne(shopDto.getId()).getItems().get(0).getName()));
    }
}