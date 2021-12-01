package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.AddressDao;
import com.amr.project.model.entity.Address;
import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Country;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressDaoImpl extends ReadWriteDaoImpl<Address, Long> implements AddressDao {
    @Override
    public Address findById(Long id) {
        return em.createQuery("select c from Address c where c.id = :id", Address.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public boolean findByAddress(Address address) {
        Country country = address.getCountry();
        City city = address.getCity();
        String cityIndex = address.getCityIndex();
        String street = address.getStreet();
        String house = address.getHouse();
        List<Address> addressList = (List<Address>) em.createQuery("select c from Address c where c.country = :country" +
                        " and c.city = :city and c.cityIndex =:cityIndex " +
                        "and c.street = :street and c.house =:house", Address.class)
                .setParameter("country", country)
                .setParameter("city", city)
                .setParameter("cityIndex", cityIndex)
                .setParameter("street", street)
                .setParameter("house", house).getResultList();
        if (addressList.size() > 0) {
            return false;

        } else {
            return true;
        }

    }

    @Override
    public Address getByAddress(Address address) {
        Country country = address.getCountry();
        City city = address.getCity();
        String cityIndex = address.getCityIndex();
        String street = address.getStreet();
        String house = address.getHouse();
        return em.createQuery("select c from Address c where c.country = :country" +
                        " and c.city = :city and c.cityIndex =:cityIndex " +
                        "and c.street = :street and c.house =:house", Address.class)
                .setParameter("country", country)
                .setParameter("city", city)
                .setParameter("cityIndex", cityIndex)
                .setParameter("street", street)
                .setParameter("house", house).getSingleResult();

    }
    @Override
    public Address findByCityIndex(String cityIndex) {
        return em.createQuery("select a from Address a where a.cityIndex=:cityIndex", Address.class)
                .setParameter("cityIndex", cityIndex).getSingleResult();
    }
}
