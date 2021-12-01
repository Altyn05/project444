package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ItemMapper;

import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Item;
import com.amr.project.repository.ItemRepository;
import com.amr.project.service.abstracts.ReadWriteService;
import com.amr.project.util.ShopItemsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ShopItemsRestController {

    private final ShopItemsUtil shopItemsUtil;
    private final ReadWriteService<Item, Long> rwItem;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @GetMapping("/shopItems")
    public List<ItemDto> getItems()  {
        return itemMapper.toListDto(itemRepository.findAllByShopIsNull().orElse(null));
    }

    @PutMapping("/shopItems")
    public ShopDto editItem(@RequestBody ShopDto shopDto) { return shopItemsUtil.updateShop(shopDto); }
}
