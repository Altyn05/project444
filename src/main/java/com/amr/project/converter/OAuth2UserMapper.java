package com.amr.project.converter;

import com.amr.project.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Mapper(componentModel = "spring")
public interface OAuth2UserMapper {
    @Mapping(target = "authorities", ignore = true)
    @Mapping(expression = "java(oauth2User.getAttribute(\"name\").toString().split(\" \")[0])", target = "firstName")
    @Mapping(expression = "java(oauth2User.getAttribute(\"name\").toString().split(\" \")[1])", target = "lastName")
    @Mapping(expression = "java(oauth2User.getAttribute(\"email\"))", target = "username")
    @Mapping(expression = "java(oauth2User.getAttribute(\"email\"))", target = "email")
    @Mapping(expression = "java(userRequest.getClientRegistration().getRegistrationId())", target = "authProvider")
    @Mapping(expression = "java(oauth2User.getAttribute(\"id\"))", target = "idProvider")
    User googleUserToUser(OAuth2User oauth2User, OAuth2UserRequest userRequest);
}
