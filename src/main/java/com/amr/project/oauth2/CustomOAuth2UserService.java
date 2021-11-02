package com.amr.project.oauth2;

import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.RoleService;
import com.amr.project.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserServiceImpl userService;
    private final RoleService roleService;
    private CustomOAuth2User oauth2User;

    @Autowired
    public CustomOAuth2UserService(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        oauth2User = new CustomOAuth2User(super.loadUser(userRequest));
        Optional<User> existingUser = userService.findUserByEmail(oauth2User.getEmail());
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
        return oauth2User;
    }

    private void updateExistingUser(User user) {
        user.setUsername(oauth2User.getEmail());
        user.setEmail(oauth2User.getEmail());
        user.setFirstName(oauth2User.getFirstName());
        user.setLastName(oauth2User.getLastName());
        userService.update(user);
    }

    private void registerNewUser(OAuth2UserRequest userRequest) {
        User user = new User();
        user.setUsername(oauth2User.getEmail());
        user.setEmail(oauth2User.getEmail());
        user.setFirstName(oauth2User.getFirstName());
        user.setLastName(oauth2User.getLastName());
        user.addRole(roleService.getRoleByName("USER"));
        user.setAuthProvider(userRequest.getClientRegistration().getRegistrationId());
        user.setIdProvider((oauth2User.getAttribute("id")));
        userService.persist(user);
    }

}
