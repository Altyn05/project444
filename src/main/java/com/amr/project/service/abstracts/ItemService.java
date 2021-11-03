package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Address;
import com.amr.project.model.entity.Item;

public interface ItemService extends ReadWriteService<Item,Long>{
    Item findById(Long id);
    Item findByName(String name);
}
