package com.amr.project.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AdminItemShortDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private List<CategoryDto> categories;
    private Double rating;
    private String description;
}
