package com.amr.project.model.dto;

import com.amr.project.converter.CategoryMapper;
import lombok.*;
import org.mapstruct.factory.Mappers;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {

    private String email;
    private String username;
    private int age;
    private Long id;
    private String gender;
    private String password;
    private String phone;
    private String firstName;
    private String lastName;
    private List<AddressDto> address;
    private List<ImageDto> images;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private List<OrderDto> orders = new ArrayList<>();
    private List<ShopDto> shops = new ArrayList<>();
    private List<CategoryDto> categories = new ArrayList<>();
    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    public UserDto(String email, String username, String password, String phone, String firstName, String lastName) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto() {
        this.email = "";
        this.username = "";
        this.password = "";
        this.phone = "";
        this.firstName = "";
        this.lastName = "";
    }

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDto(String email, String username, int age, String gender, String password, String phone, String firstName, String lastName) {
        this.email = email;
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.password = password;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
