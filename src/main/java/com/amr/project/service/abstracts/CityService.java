package com.amr.project.service.abstracts;

import com.amr.project.model.entity.City;

public interface CityService extends ReadWriteService<City, Long> {
    void addNewCity(City city);

    City findById(Long id);
    City findByName(String name);

    City checkByName(String name);
}
