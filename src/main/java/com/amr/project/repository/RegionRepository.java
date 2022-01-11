package com.amr.project.repository;

import com.amr.project.model.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Region findRegionById(Long id);

    Region findRegionByName(String name);
}
