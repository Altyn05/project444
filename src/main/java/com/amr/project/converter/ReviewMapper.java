package com.amr.project.converter;

import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    List<ReviewDto> reviewListToListReviewDto (List<Review> list);
    @Mapping(target = "shopName", source = "shop.name")
    ReviewDto reviewToReviewDto(Review review);
    Review toModel(ReviewDto reviewDto);
}
