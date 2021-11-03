package com.amr.project.oauth2;

import com.amr.project.converter.OAuth2UserMapper;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.RoleService;
import com.amr.project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends OidcUserService {

    private final UserServiceImpl userService;
    private final RoleService roleService;
    private final OAuth2UserMapper userMapper;


    @Autowired
    public CustomOidcUserService(UserServiceImpl userService, RoleService roleService, OAuth2UserMapper userMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {

        CustomOidcUser oidcUser = new CustomOidcUser(super.loadUser(userRequest));
        User user = userMapper.oidcUserToUser(oidcUser, userRequest);
        user.addRole(roleService.getRoleByName("USER"));

        User existingUser = userService.findUserByEmail(oidcUser.getEmail()).orElse(null);
        if (existingUser != null) {
            user.setId(existingUser.getId());
            userService.update(user);
        } else {
            userService.persist(user);
        }

        return oidcUser;
    }

}
