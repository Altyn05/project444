package com.amr.project.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AdminItemDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private List<CategoryDto> categories;
    private AdminShopShortDto shop;
    private Double rating;
    private String description;
}
