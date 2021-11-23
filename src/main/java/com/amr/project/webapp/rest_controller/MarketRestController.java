package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.CityService;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/market/api")
public class MarketRestController {
    private ReadWriteService<Shop, Long> shopService;

    private ShopMapper shopMapper;
    private ItemService itemService;
    CityService cityService;

    public MarketRestController(ReadWriteService<Shop, Long> shopService, ShopMapper shopMapper, ItemService itemService, CityService cityService) {
        this.shopService = shopService;
        this.shopMapper = shopMapper;
        this.itemService = itemService;
        this.cityService = cityService;
    }

    @GetMapping("/info/{id}")
    public ShopDto getMarketInfo(@PathVariable(value = "id", required = true) Long id) {
        return shopService.getByKey(Shop.class, id).map(shopMapper::shopToDto).orElse(new ShopDto());
    }

}





