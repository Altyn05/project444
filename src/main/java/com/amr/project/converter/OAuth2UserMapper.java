package com.amr.project.converter;

import com.amr.project.model.entity.User;
import com.github.openjson.JSONObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
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
    User oauth2UserToUser(OAuth2User oauth2User, OAuth2UserRequest userRequest);

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(source = "oidcUser.givenName", target = "firstName")
    @Mapping(source = "oidcUser.familyName", target = "lastName")
    @Mapping(source = "oidcUser.email", target = "username")
    @Mapping(source = "oidcUser.email", target = "email")
    @Mapping(expression = "java(userRequest.getClientRegistration().getRegistrationId())", target = "authProvider")
    @Mapping(expression = "java(oidcUser.getAttribute(\"sub\"))", target = "idProvider")
    User oidcUserToUser(OidcUser oidcUser, OidcUserRequest userRequest);

    @Mapping(expression = "java(jsonUser.getString(\"first_name\"))", target = "firstName")
    @Mapping(expression = "java(jsonUser.getString(\"last_name\"))", target = "lastName")
    @Mapping(source = "email", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "provider", target = "authProvider")
    @Mapping(expression = "java(jsonUser.getString(\"id\"))", target = "idProvider")
    User vkUserToUser(JSONObject jsonUser, String provider, String email);

    @Mapping(expression = "java(jsonUser.getString(\"first_name\"))", target = "firstName")
    @Mapping(expression = "java(jsonUser.getString(\"last_name\"))", target = "lastName")
    @Mapping(expression = "java(jsonUser.getString(\"email\"))", target = "username")
    @Mapping(expression = "java(jsonUser.getString(\"email\"))", target = "email")
    @Mapping(expression = "java(jsonUser.getInt(\"age\"))", target = "age")
    @Mapping(source = "provider", target = "authProvider")
    @Mapping(expression = "java(jsonUser.getString(\"uid\"))", target = "idProvider")
    User okUserToUser(JSONObject jsonUser, String provider);

}
