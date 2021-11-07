package com.amr.project.webapp.rest_controller;

import com.amr.project.model.dto.ItemMainPageDTO;
import com.amr.project.model.dto.ShopMainPageDTO;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MainPageRestController {

    private final ItemService itemService;
    private final ShopService shopService;

    @GetMapping("/items")
    public Page<ItemMainPageDTO> getPopularItems(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return itemService.findPagedPopularItems(PageRequest.of(page, size));
    }

    @GetMapping("/shops")
    public Page<ShopMainPageDTO> getPopularShops(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return shopService.findPagedPopularShops(PageRequest.of(page, size));
    }
}
