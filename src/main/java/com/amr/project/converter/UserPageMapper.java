package com.amr.project.converter;

import com.amr.project.model.dto.UserChatDTO;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.dto.UserPageDto;
import com.amr.project.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {AddressMapper.class})
public interface UserPageMapper {

    @Mapping(target = "id", source = "user.id")
    UserPageDto toDto(User user);
    User toModel(UserPageDto userPageDto);

    @Mapping(target = "address", ignore = true)     //листы пока не обновляю
    @Mapping(target = "images", ignore = true)      //листы пока не обновляю
    void updateModel (UserPageDto userPageDto, @MappingTarget User user);

    List<UserChatDTO> listUserToListUserChatDTO(List<User> list);
    UserChatDTO userToChatDTO(User user);
}
