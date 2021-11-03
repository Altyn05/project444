package com.amr.project.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Data
public class UserDto {

    private String email;
    private String username;
    private int age;
    private String gender;
    private String password;
    private String phone;
    private String firstName;
    private String lastName;
    private List<AddressDto> address;
    private List<ImageDto> images;
    private Calendar birthday;
    private List<OrderDto> orders = new ArrayList<>();
    private List<ShopDto> shops = new ArrayList<>();

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
