package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Country;

public interface CountryDao extends ReadWriteDao<Country, Long> {
    Country findById(Long id);
    Country findByName(String name);

    boolean checkByName(String name);
}
