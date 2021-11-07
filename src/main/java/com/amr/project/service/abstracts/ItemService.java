package com.amr.project.service.abstracts;

import com.amr.project.model.dto.ItemMainPageDTO;
import com.amr.project.model.entity.Address;
import com.amr.project.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService extends ReadWriteService<Item,Long>{
    Item findById(Long id);
    Item findByName(String name);
    Page<ItemMainPageDTO> findPagedPopularItems(Pageable pageable);
}
