package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.CountryDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Category;
import com.amr.project.model.entity.Country;
import com.amr.project.model.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public class CountryDaoImpl extends ReadWriteDaoImpl<Country,Long> implements CountryDao {

    @Override
    public Country findById(Long id){
        return em.find(Country.class, id);
    }

    @Override
    public Country findByName(String name) {
        return em.createQuery("select c from Country c where c.name=:name", Country.class)
                .setParameter("name", name).getSingleResult();
    }
}
