package com.amr.project.oauth2;

import com.amr.project.converter.OAuth2UserMapper;
import com.amr.project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends OidcUserService {

    private final UserServiceImpl userService;
    private final OAuth2UserMapper userMapper;
    private final UserProcessingService processingService;


    @Autowired
    public CustomOidcUserService(UserServiceImpl userService, OAuth2UserMapper userMapper,
                                 UserProcessingService processingService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.processingService = processingService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {

        CustomOidcUser oidcUser = new CustomOidcUser(super.loadUser(userRequest));

        processingService.process(
                userMapper.oidcUserToUser(oidcUser, userRequest),
                userService.findUserByEmail(oidcUser.getEmail()).orElse(null),
                false
        );

        return oidcUser;
    }

}
