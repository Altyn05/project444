package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Address;

public interface AddressService extends ReadWriteService<Address, Long>{
    void addNewAddress(Address address);

    Address getByAddress(Address address);

    Address findById(Long id);

    Address findByCityIndex(String index);

}
