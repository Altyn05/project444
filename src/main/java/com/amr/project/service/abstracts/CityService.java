package com.amr.project.service.abstracts;

import com.amr.project.model.entity.City;


public interface CityService extends ReadWriteService<City, Long> {
    City findById(Long id);
    City findByName(String name);
}
