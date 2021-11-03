package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.CityDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.City;
import com.amr.project.service.abstracts.CityService;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends ReadWriteServiceImpl<City,Long> implements CityService {

    private final CityDao cityDao;

    protected CityServiceImpl(ReadWriteDao<City, Long> readWriteDao, CityDao cityDao) {
        super(readWriteDao);
        this.cityDao = cityDao;
    }

    @Override
    public City findById(Long id) {
        return cityDao.findById(id);
    }

    @Override
    public City findByName(String name) {
        return cityDao.findByName(name);
    }
}
