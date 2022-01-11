package com.amr.project.converter;

import com.amr.project.model.dto.AdminRegionDto;
import com.amr.project.model.entity.Region;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegionMapper {
    List<AdminRegionDto> regionListToListAdminRegionDto(List<Region> regions);
    AdminRegionDto regionToAdminRegionDto(Region region);
}
