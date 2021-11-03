package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Address;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopDao extends ReadWriteDao<Shop,Long> {
    Shop findById(Long id);
    Shop findByName(String name);
    List<Shop> findPopularShops();
    List<Shop> searchShops(String search);
}
