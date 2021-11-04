package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import com.amr.project.repository.ItemRepository;
import com.amr.project.service.abstracts.ReadWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ItemRestController {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final ReadWriteService<Item,Long> rwItem;

    @PostMapping("/item")
    public ItemDto addItem(@RequestBody ItemDto itemDto) {
        rwItem.persist(itemMapper.toModel(itemDto));
        return itemMapper.toDto(itemRepository.findItemByName(itemDto.getName()).orElse(null));
    }

    @PutMapping("/item")
    public ItemDto editItem(@RequestBody ItemDto itemDto) {
        rwItem.update(itemMapper.toModel(itemDto));
        return itemMapper.toDto(itemRepository.findItemByName(itemDto.getName()).orElse(null));
    }

    @DeleteMapping("/item/{id}")
    public String deleteItem(@PathVariable Long id) {
        rwItem.delete(rwItem.getByKey(Item.class, id).orElse(null));
        return "{  \"result\" : \"OK\" }";
    }
}
