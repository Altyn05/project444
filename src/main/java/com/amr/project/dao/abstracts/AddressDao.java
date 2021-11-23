package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Address;

public interface AddressDao extends ReadWriteDao<Address, Long>  {

    public Address findById(Long id);

    boolean findByAddress(Address address);

    Address getByAddress(Address address);

    Address findByCityIndex(String cityIndex);

}
