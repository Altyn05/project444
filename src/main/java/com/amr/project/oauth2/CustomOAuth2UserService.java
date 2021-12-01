package com.amr.project.oauth2;

import com.amr.project.converter.OAuth2UserMapper;
import com.amr.project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserServiceImpl userService;
    private final OAuth2UserMapper userMapper;
    private final UserProcessingService processingService;


    @Autowired
    public CustomOAuth2UserService(UserServiceImpl userService, OAuth2UserMapper userMapper,
                                   UserProcessingService processingService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.processingService = processingService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        CustomOAuth2User oauth2User = new CustomOAuth2User(super.loadUser(userRequest));

        processingService.process(
                userMapper.oauth2UserToUser(oauth2User, userRequest),
                userService.findUserByEmail(oauth2User.getEmail()).orElse(null),
                false
        );

        return oauth2User;
    }

}
