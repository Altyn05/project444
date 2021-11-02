package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.AddressDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Address;
import com.amr.project.service.abstracts.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ReadWriteServiceImpl<Address, Long> implements AddressService {

    AddressDao addressDao;

    public AddressServiceImpl(ReadWriteDao<Address, Long> readWriteDao, AddressDao addressDao) {
        super(readWriteDao);
        this.addressDao = addressDao;
    }

    @Override
    public Address findById(Long id) {
        return addressDao.findById(id);
    }

    @Override
    public Address findByCityIndex(String index) {
        return addressDao.findByCityIndex(index);
    }
}
