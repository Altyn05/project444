package com.amr.project.converter;

import com.amr.project.model.dto.*;
import com.amr.project.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring",
        uses = {OrderMapper.class,
                AddressMapper.class})
public interface UserMapper {
    @Mapping(target = "id", source = "user.id")
    UserDto toDto(User user);
    User toModel(UserDto userDto);                //это не работает тк половины полей в UserDto нет

    @Mapping(target = "address", ignore = true)     //листы пока не обновляю
    @Mapping(target = "images", ignore = true)      //листы пока не обновляю
    @Mapping(target = "orders", ignore = true)      //листы пока не обновляю
    @Mapping(target = "shops", ignore = true)       //листы пока не обновляю
    void updateModel (UserDto userDto, @MappingTarget User user);

    List<UserChatDTO> listUserToListUserChatDTO(List<User> list);
    UserChatDTO userToChatDTO(User user);
}