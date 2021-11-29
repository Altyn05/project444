package com.amr.project.service.abstracts;

import com.amr.project.model.dto.ItemMainPageDTO;
import com.amr.project.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService extends ReadWriteService<Item,Long>{
    Item findById(Long id);
    Item findByName(String name);
    Page<ItemMainPageDTO> findPagedPopularItems(Pageable pageable);
    Page<ItemMainPageDTO> findPagedItemsByCategoryId(Long categoryId, Pageable pageable);
    Page<ItemMainPageDTO> searchPagedItems(String search, Pageable pageable);
    Page<ItemMainPageDTO> searchPagedItemsByCategoryId(String search, Long cateegoryId, Pageable pageable);

    List<Item> getNotModeratedItems();
    Item rejectItem(Long id, String rejectReason);
    Item approveItem(Long id);

}
