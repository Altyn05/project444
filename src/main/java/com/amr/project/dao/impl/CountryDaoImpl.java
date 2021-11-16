package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.CountryDao;
import com.amr.project.model.entity.Country;
import com.amr.project.model.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryDaoImpl extends ReadWriteDaoImpl<Country, Long> implements CountryDao {

    @Override
    public Country findByName(String name) {
        return em.createQuery("select c from Country c where c.name like :name", Country.class)
                .setParameter("name", name).getSingleResult();

    }

    @Override
    public boolean getByName(String name) {
        List<Country> listCountry =
                (List<Country>) em.createQuery("select uf from Country uf where uf.name like :name", Country.class).
                        setParameter("name", name).getResultList();
        if (listCountry.size() > 0) {
            return false;
        } else return true;

    }
    @Override
    public Country findById(Long id){
        return em.find(Country.class, id);
    }
    @Override
    public boolean checkByName(String name) {
        List<Country> listCountry =
                (List<Country>) em.createQuery("select uf from Country uf where uf.name like :name", Country.class).
                        setParameter("name", name).getResultList();
        return listCountry.size() ==0;

    }
}
