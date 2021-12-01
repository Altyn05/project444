package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.City;

public interface CityDao extends ReadWriteDao<City, Long> {
    City findById(Long id);
    City findByName(String name);

    boolean checkByName(String name);
}
