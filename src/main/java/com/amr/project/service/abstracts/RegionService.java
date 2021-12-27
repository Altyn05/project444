package com.amr.project.service.abstracts;

import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Region;

import java.util.List;

public interface RegionService extends ReadWriteService<Region, Long> {

    List<Region> findAll();

    void persist(Region region);

    void update(Region region);

    void delete(Region region);

    Region findById(Long id);

    Region findByName(String name);
}
