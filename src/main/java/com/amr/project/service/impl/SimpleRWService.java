package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import org.springframework.stereotype.Service;

@Service("SimpleRWService")
public class SimpleRWService<T, ID> extends ReadWriteServiceImpl<T, ID> {
    public SimpleRWService(ReadWriteDao<T, ID> readWriteDao) {
        super(readWriteDao);
    }
}
