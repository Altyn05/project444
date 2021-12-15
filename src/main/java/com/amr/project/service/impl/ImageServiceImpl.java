package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ImageDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Image;
import com.amr.project.service.abstracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
//@Transactional
public class ImageServiceImpl extends ReadWriteServiceImpl<Image, Long> implements ImageService {

    @Autowired
    private ImageDao imageDao;

    protected ImageServiceImpl(ReadWriteDao<Image, Long> readWriteDao) {
        super(readWriteDao);
    }

    @Override
    public void addNewImage(Image image) {
        imageDao.persist(image);
    }
}
