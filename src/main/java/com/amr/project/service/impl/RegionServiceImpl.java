package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Region;
import com.amr.project.repository.RegionRepository;
import com.amr.project.service.abstracts.RegionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RegionServiceImpl extends ReadWriteServiceImpl<Region, Long> implements RegionService {
    RegionRepository regionRepository;

    protected RegionServiceImpl(ReadWriteDao<Region, Long> readWriteDao, RegionRepository regionRepository) {
        super(readWriteDao);
        this.regionRepository = regionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    @Override
    public void persist(Region region) {
        regionRepository.save(region);
    }

    @Override
    public void update(Region region){
        persist(region);
    }

    @Override
    public void delete(Region region) {
        regionRepository.delete(region);
    }

    @Override
    public Region findById(Long id) {
        return regionRepository.findRegionById(id);
    }

    @Override
    public Region findByName(String name) {
        return regionRepository.findRegionByName(name);
    }
}
