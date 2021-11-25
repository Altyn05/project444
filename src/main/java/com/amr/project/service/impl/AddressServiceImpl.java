package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.AddressDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Address;
import com.amr.project.service.abstracts.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ReadWriteServiceImpl<Address, Long> implements AddressService {
    private final AddressDao addressDao;

    protected AddressServiceImpl(ReadWriteDao<Address, Long> readWriteDao, AddressDao addressDao) {
        super(readWriteDao);
        this.addressDao = addressDao;
    }
    @Override
    public Address findByCityIndex(String index) {
        return addressDao.findByCityIndex(index);
    }
    @Override
    public void addNewAddress(Address address) {
        if (addressDao.findByAddress(address)) {
            addressDao.persist(address);
        }
//        else {
//            Long id = addressDao.getByAddress(address).getId();
//            if (id != null) {
//                address.setId(id);
//                addressDao.update(address);
//            }
        }

    @Override
    public Address getByAddress(Address address) {
        return addressDao.getByAddress(address);
    }
    @Override
    public Address findById(Long id) {
        return addressDao.findById(id);
    }
}
