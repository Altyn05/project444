package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.CityDao;
import com.amr.project.model.entity.City;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityDaoImpl extends ReadWriteDaoImpl<City, Long> implements CityDao {

    @Override
    public City findByName(String name) {
        return em.createQuery("select c from City c where c.name like :name", City.class).setParameter("name", name).getSingleResult();
    }
    @Override
    public City findById(Long id) {
        return em.find(City.class, id);
    }

       @Override
    public boolean checkByName(String name) {
        List<City> listCity = (List<City>) em.createQuery("select uf from City uf where uf.name like :name", City.class).
                setParameter("name", name).getResultList();
        return listCity.size() == 0;
    }
}

