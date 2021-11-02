package com.amr.project.converter;

import com.amr.project.model.dto.*;
import com.amr.project.model.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "username", source = ".")
    UserDto toDto(User user);

//    User toModel(UserDto userDto);

    @Mapping(target = "city", source = "city.name")
    @Mapping(target = "country", source = "country.name")
    AddressDto addressToAddressDto(Address address);
    ImageDto imageToImageDto(Image image);
    OrderDto orderToOrderDto(Order order);

    List<AdminUserDto> userListToListAdminUserDto(List<User> list);

    AdminUserDto userToAdminUserDto(User user);

    List<UserDto> userListToListUserDto(List<User> list);

    default String getUserName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }
}