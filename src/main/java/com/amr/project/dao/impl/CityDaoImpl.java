package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.CityDao;
import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public class CityDaoImpl extends ReadWriteDaoImpl<City, Long> implements CityDao {
    @Override
    public City findById(Long id) {
        return em.find(City.class, id);
    }

    @Override
    public City findByName(String name) {
        return em.createQuery("select c from City c where c.name=:name", City.class)
                .setParameter("name", name).getSingleResult();
    }
}
