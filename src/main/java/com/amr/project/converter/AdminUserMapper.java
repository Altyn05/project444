package com.amr.project.converter;

import com.amr.project.model.dto.AdminUserDto;
import com.amr.project.model.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminUserMapper {
    List<AdminUserDto> userListToListAdminUserDto(List<User> list);
    AdminUserDto userToAdminUserDto(User user);
}
