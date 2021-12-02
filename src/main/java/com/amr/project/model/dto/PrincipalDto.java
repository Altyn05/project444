package com.amr.project.model.dto;

import lombok.Data;

@Data
public class PrincipalDto {
    private String username;
    private int age;
    private String gender;
    private String email;
    private String phone;
    private AddressDto address;
    private ImageDto images;
}
