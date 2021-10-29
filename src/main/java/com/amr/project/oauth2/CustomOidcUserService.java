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
        Optional<User> existingUser = userService.findUserByIdProvider(oidcUser.getAttribute("sub"));
        if (existingUser.isPresent()) {
            updateExistingUser(existingUser.get());
        } else {
            registerNewUser(userRequest);
        }
        return  oidcUser;
    }

    private void updateExistingUser(User user) {
        user.setUsername(oidcUser.getName());
        user.setEmail(oidcUser.getEmail());
        userService.update(user);
    }

    private void registerNewUser(OAuth2UserRequest userRequest) {
        User user = new User();
        user.setUsername(oidcUser.getName());
        user.setEmail(oidcUser.getEmail());
        user.addRole(roleService.getRoleByName("USER"));
        Image userImage = new Image();
        userImage.ImageFromURL(oidcUser.getPicture());
        user.addImage(userImage);
        user.setAuthProvider(userRequest.getClientRegistration().getRegistrationId());
        user.setIdProvider((oidcUser.getAttribute("sub")));
        user.getImages().forEach(imageService::persist);
        userService.persist(user);
    }

}
