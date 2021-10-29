package com.amr.project.oauth2;

import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ImageService;
import com.amr.project.service.abstracts.RoleService;
import com.amr.project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        oidcUser = new CustomOidcUser(super.loadUser(userRequest));
        processAuthUser(userRequest);
        return  oidcUser;
    }

    private void processAuthUser(OAuth2UserRequest userRequest) {
        if (Objects.isNull(oidcUser.getEmail())) {
            throw new OAuth2AuthenticationException(new OAuth2Error("При аутентификации oidc-пользователя не найден email"));
        }

        Optional<User> OptionalUser = userService.findUserByEmail(oidcUser.getEmail());
        User existingUser;
        if (OptionalUser.isPresent()) {
            existingUser = OptionalUser.get();
            if (!existingUser.getAuthProvider().equals(userRequest.getClientRegistration().getRegistrationId())) {
                throw new OAuth2AuthenticationException(new OAuth2Error(
                        "Вы уже зарегистрированы с учетной записью " + existingUser.getAuthProvider() +
                                ". Пожалйста, войдите со своего " + existingUser.getAuthProvider() + " аккаунта."
                ));
            }
            updateExistingUser(existingUser, oidcUser);
        } else {
            registerNewUser(userRequest, oidcUser);
        }
    }

    private void updateExistingUser(User user, OidcUser oidcUser) {
        user.setUsername(oidcUser.getName());
        user.setFirstName(oidcUser.getGivenName());
        user.setLastName(oidcUser.getFamilyName());
        userService.update(user);
    }

    private void registerNewUser(OAuth2UserRequest userRequest, OidcUser oidcUser) {
        User user = new User();
        user.setFirstName(oidcUser.getGivenName());
        user.setLastName(oidcUser.getFamilyName());
        user.setUsername(oidcUser.getName());
        user.setEmail(oidcUser.getEmail());
        user.addRole(roleService.getRoleByName("USER"));
        Image userImage = new Image();
        userImage.ImageFromURL(oidcUser.getPicture());
        user.addImage(userImage);
        user.setAuthProvider(userRequest.getClientRegistration().getRegistrationId());
        user.setIdProvider((oidcUser.getIdToken().getClaim("sub")));
        user.getImages().forEach(imageService::persist);
        userService.persist(user);
    }

}
