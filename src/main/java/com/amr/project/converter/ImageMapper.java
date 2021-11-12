package com.amr.project.converter;

import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.entity.Image;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDto toDto(Image image);
    Image toModel(ImageDto imageDto);
    List<Image> toListModel(List<ImageDto> images);
}
