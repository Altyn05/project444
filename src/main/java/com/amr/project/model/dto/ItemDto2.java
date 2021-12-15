package com.amr.project.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDto2 {
    private Long id;
    private String name;
    private Integer count;
    private BigDecimal price;
    private Double rating;
    private String description;
    private String shopName;
    private String shopPhone;
    private ImageDto shopLogo;
    private CountryDto shopLocation;
    private byte[] picture;
    private String url;
    private List<ImageDto> images;
    private boolean favorite;

}
