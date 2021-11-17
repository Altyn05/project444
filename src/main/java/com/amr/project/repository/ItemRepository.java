package com.amr.project.repository;

import com.amr.project.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemByName (String name);
    Optional<List<Item>> findAllByShopIsNull();
}
