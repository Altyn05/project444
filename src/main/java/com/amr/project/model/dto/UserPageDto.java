package com.amr.project.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class UserPageDto {

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
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    public UserPageDto(String email, String username, String password, String phone, String firstName, String lastName) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserPageDto() {
        this.email = "";
        this.username = "";
        this.password = "";
        this.phone = "";
        this.firstName = "";
        this.lastName = "";
    }

    public UserPageDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserPageDto(String email, String username, int age, String gender, String password, String phone,
                   String firstName, String lastName) {
        this.email = email;
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.password = password;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
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
