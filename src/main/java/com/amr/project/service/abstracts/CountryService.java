package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Country;

public interface CountryService extends ReadWriteService<Country, Long>{
    Country findById(Long id);
    Country findByName(String name);

    void addNewCountry(Country country);

    Country checkByName(String name);
}
