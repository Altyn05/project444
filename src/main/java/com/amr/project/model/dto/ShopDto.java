package com.amr.project.model.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShopDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String description;
    private CountryDto location;
    private CityDto cityDto;
    private List<ItemDto> items;
    private ImageDto logo;
    private List<ImageDto> images;
    private double rating;
    private String username;
    private Long user_id;
    private boolean favorite;
    private boolean isModerated;
    private boolean isModerateAccept;
    private String moderatedRejectReason;
    private boolean isPretendentToBeDeleted;
    private List<ReviewDto> reviews;

    public Long countRating() {
        if (!reviews.isEmpty()) {
            return Math.round(reviews.stream().mapToDouble(ReviewDto::getRating).sum() / reviews.size());
        }
        return null;
    }

    public String printRating() {
        if (!reviews.isEmpty()) {
            return String.format("%.1f", reviews.stream().mapToDouble(ReviewDto::getRating).sum() / reviews.size());
        }
        return null;
    }
}
