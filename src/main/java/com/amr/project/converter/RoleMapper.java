package com.amr.project.converter;

import com.amr.project.model.dto.RoleDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Role;
import com.amr.project.model.entity.Shop;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toModel(RoleDto roleDto);
    List<RoleDto> roleListToListRoleDto(List<Role> list);
}
