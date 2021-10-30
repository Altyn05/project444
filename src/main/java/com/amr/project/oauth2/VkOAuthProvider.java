package com.amr.project.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

public class VkOAuthProvider {

    public ClientRegistrationRepository RegistrationVkRepository() {
        return new InMemoryClientRegistrationRepository(this.vkClientRegistration());
    }

    public ClientRegistration vkClientRegistration() {
        return ClientRegistration.withRegistrationId("vk")
                .clientId("7987723")
                .clientSecret("tS6bkYtW2OW18WCKQsTK")
                .clientAuthenticationMethod(ClientAuthenticationMethod.POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8888/login/oauth2/code/vk")
                .scope("email")
                .authorizationUri("https://oauth.vk.com/authorize")
                .tokenUri("https://oauth.vk.com/access_token")
                .userInfoUri("https://api.vk.com/method/users.get?{user_id}&v=5.95&fields=photo_id,verified,sex,bdate,city,country,photo_max,home_town,has_photo&display=popup&lang=ru&access_token=xxxxx")
                .userNameAttributeName("user_id")
                .clientName("vkontakte")
                .build();
    }
}
