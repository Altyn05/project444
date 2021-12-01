package com.amr.project.model.dto;

import com.amr.project.model.enums.Gender;
import lombok.Data;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Data
public class AdminUserDto {

    private Long id;
    private String email;
    private String username;
    private String password;
    private boolean activate;
    private String phone;
    private String firstName;
    private String lastName;
    private int age;
    private List<AdminAddressDto> address;
    private Set<RoleDto> roles;
    private Gender gender;
    private Calendar birthday;
    private List<AdminShopShortDto> shops;
}
