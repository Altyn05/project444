package com.amr.project.converter;

import com.amr.project.model.dto.*;
import com.amr.project.model.entity.User;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {OrderMapper.class,
                AddressMapper.class})
public interface UserMapper {
    UserDto toDto(User user);
    User toModel(UserDto userDto);
}