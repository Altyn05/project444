package com.amr.project.model.dto;

import com.amr.project.model.entity.Country;
import lombok.Data;

@Data
public class AdminShopShortDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Country location;
}
