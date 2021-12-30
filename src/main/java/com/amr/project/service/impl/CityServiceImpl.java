package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.CityDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.City;
import com.amr.project.service.abstracts.CityService;
import com.amr.project.service.abstracts.CountryService;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends ReadWriteServiceImpl<City, Long> implements CityService {

    private final CityDao cityDao;
    private final CountryService countryService;

    protected CityServiceImpl(ReadWriteDao<City, Long> readWriteDao, CityDao cityDao, CountryService countryService) {
        super(readWriteDao);
        this.cityDao = cityDao;
        this.countryService = countryService;
    }

    @Override
    public void addNewCity(City city) {
        if (countryService.findByName(city.getCountry().getName()).getRegions().contains(city.getRegion())) {
            cityDao.persist(city);
        } else {
            System.err.println("Region doesn't exist in this country");
        }
    }

    @Override
    public void updateCity(City city) {
        if (countryService.findByName(city.getCountry().getName()).getRegions().contains(city.getRegion())) {
            cityDao.update(city);
        } else {
            System.err.println("Region doesn't exist in this country");
        }
    }

    @Override
    public City findById(Long id) {
        return cityDao.findById(id);
    }

    @Override
    public City findByName(String name) {
        return cityDao.findByName(name);
    }

    @Override
    public City checkByName(String name) {
        if (cityDao.checkByName(name)) {
            addNewCity(new City(name));
        }
        return cityDao.findByName(name);
    }

}
