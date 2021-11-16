package com.amr.project.converter;

import com.amr.project.model.dto.CategoryDto;
import com.amr.project.model.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    List<CategoryDto> categoryListToListCategoryDTO(List<Category> list);
    CategoryDto categoryToCategoryDto(Category category);
}