package com.amr.project.oauth2;

import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ImageService;
import com.amr.project.service.abstracts.RoleService;
import com.amr.project.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomOidcUserService extends OidcUserService {

    private final UserServiceImpl userService;
    private final ImageService imageService;
    private final RoleService roleService;
    private CustomOidcUser oidcUser;

    @Autowired
    public CustomOidcUserService(UserServiceImpl userService, ImageService imageService, RoleService roleService) {
        this.userService = userService;
        this.imageService = imageService;
        this.roleService = roleService;
    }

    @SneakyThrows
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        oidcUser = new CustomOidcUser(super.loadUser(userRequest));
        Optional<User> existingUser = userService.findUserByEmail(oidcUser.getEmail());
        if (existingUser.isPresent()) {
            String providerName = existingUser.get().getAuthProvider();
            if (!userRequest.getClientRegistration().getRegistrationId().equals(providerName)) {
                throw new Exception(
                        "Вы уже зарегистрированы. Пожалуйста, войдите со своим " + providerName + " аккаунтом."
                );
            }
            updateExistingUser(existingUser.get());
        } else {
            registerNewUser(userRequest);
        }
        return  oidcUser;
    }

    private void updateExistingUser(User user) {
        user.setUsername(oidcUser.getEmail());
        user.setEmail(oidcUser.getEmail());
        user.setFirstName(oidcUser.getGivenName());
        user.setLastName(oidcUser.getFamilyName());
        userService.update(user);
    }

    private void registerNewUser(OAuth2UserRequest userRequest) {
        User user = new User();
        user.setUsername(oidcUser.getEmail());
        user.setEmail(oidcUser.getEmail());
        user.setFirstName(oidcUser.getGivenName());
        user.setLastName(oidcUser.getFamilyName());
        roleService.getRoleByName("USER").ifPresent(user::addRole);
        Image userImage = new Image();
        userImage.ImageFromURL(oidcUser.getPicture());
        user.addImage(userImage);
        user.setAuthProvider(userRequest.getClientRegistration().getRegistrationId());
        user.setIdProvider((oidcUser.getAttribute("sub")));
        user.getImages().forEach(imageService::persist);
        userService.persist(user);
    }

}
