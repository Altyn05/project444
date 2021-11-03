package com.amr.project.oauth2;

import com.amr.project.converter.OAuth2UserMapper;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.RoleService;
import com.amr.project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserServiceImpl userService;
    private final RoleService roleService;
    private final OAuth2UserMapper userMapper;

    @Autowired
    public CustomOAuth2UserService(UserServiceImpl userService, RoleService roleService, OAuth2UserMapper userMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        CustomOAuth2User oauth2User = new CustomOAuth2User(super.loadUser(userRequest));
        User user = userMapper.oauth2UserToUser(oauth2User, userRequest);
        user.addRole(roleService.getRoleByName("USER"));

        User existingUser = userService.findUserByEmail(oauth2User.getEmail()).orElse(null);
        if (existingUser != null) {
            user.setId(existingUser.getId());
            userService.update(user);
        } else {
            userService.persist(user);
        }

        return oauth2User;
    }

}
