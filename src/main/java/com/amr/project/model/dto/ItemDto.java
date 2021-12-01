package com.amr.project.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDto {
    private Long id;
    private String name;
    private String[] categoriesName;
    private Integer count;
    private BigDecimal price;
    private List<ImageDto> images;
    private boolean favorite;
    private Double rating;
    private String description;
    private String shopName;
    private String shopPhone;
    private ImageDto shopLogo;
    private CountryDto shopLocation;
    private byte[] picture;
    private String url;

    private boolean isModerateAccept;
    private boolean isModerated;
    private boolean isPretendentToBeDeleted;
    private String moderatedRejectReason;
    private List<CategoryDto> categories;
    private List<ReviewDto> reviews;
    private Long shopId;

    public String printRating() {
        if (!reviews.isEmpty()) {
            return String.format("%.1f", reviews.stream().mapToDouble(ReviewDto::getRating).sum() / reviews.size());
        }
        return null;
    }

    public Long countRating() {
        if (!reviews.isEmpty()) {
            return Math.round(reviews.stream().mapToDouble(ReviewDto::getRating).sum() / reviews.size());
        }
        return null;
    }

}
