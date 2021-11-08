package com.amr.project.webapp.rest_controller;

import com.amr.project.model.dto.ItemMainPageDTO;
import com.amr.project.model.dto.ShopMainPageDTO;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class MainPageRestController {

    private final ItemService itemService;
    private final ShopService shopService;

    @GetMapping("/items")
    public Page<ItemMainPageDTO> getPopularItems(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "categoryId", defaultValue = "0") long id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "4") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        if (search.isBlank()) {
            return id == 0
                    ? itemService.findPagedPopularItems(pageable)
                    : itemService.findPagedItemsByCategoryId(id, pageable);
        } else {
            return id == 0
                    ? itemService.searchPagedItems(search, pageable)
                    : itemService.searchPagedItemsByCategoryId(search, id, pageable);
        }
    }

    @GetMapping("/shops")
    public Page<ShopMainPageDTO> getPopularShops(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "categoryId", defaultValue = "0") long id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "6") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        if (search.isBlank()) {
            return id == 0
                    ? shopService.findPagedPopularShops(pageable)
                    : shopService.findPagedShopsByCategoryId(id, pageable);
        } else {
            return id == 0
                    ? shopService.searchPagedShops(search, pageable)
                    : shopService.searchPagedShopsByCategoryId(search, id, pageable);
        }
    }
}
