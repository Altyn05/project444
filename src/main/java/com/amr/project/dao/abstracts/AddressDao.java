package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Address;

public interface AddressDao extends ReadWriteDao<Address, Long>  {
    Address findById(Long id);
    Address findByCityIndex(String cityIndex);
}
