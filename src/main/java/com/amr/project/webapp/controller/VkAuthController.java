package com.amr.project.webapp.controller;

import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.RoleService;
import com.amr.project.service.abstracts.UserService;
import com.github.openjson.JSONObject;
import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.apis.vk.VKOAuth2AccessToken;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.AccessTokenRequestParams;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/oauth2/vk")
public class VkAuthController {

    private final OAuth20Service vkService;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public VkAuthController(@Qualifier("vkOAuthService") OAuth20Service vkService,
                            UserService userService, RoleService roleService) {
        this.vkService = vkService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String openVkLoginForm() {

        final String authorizationUrl = vkService.createAuthorizationUrlBuilder()
                .scope(vkService.getDefaultScope())
                .build();

        return "redirect:" + authorizationUrl;
    }

    @GetMapping("/code")
    public String processOAuth2User(@RequestParam("code") String code) throws Exception {

        OAuth2AccessToken accessToken = getAccessToken(code);

        if (accessToken instanceof VKOAuth2AccessToken) {
            User user = getUserByToken(accessToken);
            Optional<User> existingUser = userService.findUserByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                if (!user.getAuthProvider().equals(existingUser.get().getAuthProvider())) {
                    throw new Exception("Пользователь с таким имейл уже существует. Войдите со своим " + existingUser.get().getAuthProvider() + " аккаунтом.");
                }
                user.setId(existingUser.get().getId());
                userService.update(user);
            } else {
                userService.persist(user);
            }
            authenticate(user);
        }
        return "redirect:/";
    }

    private void authenticate(User user) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    private OAuth2AccessToken getAccessToken(String code) throws IOException, ExecutionException, InterruptedException {
        return vkService.getAccessToken(AccessTokenRequestParams
                        .create(code)
                        .scope(vkService.getDefaultScope()));
    }

    private User getUserByToken(OAuth2AccessToken accessToken) throws IOException, ExecutionException, InterruptedException {
        User user = new User();

        final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.vk.com/method/users.get?v=" + VkontakteApi.VERSION);
        vkService.signRequest(accessToken, request);
        try (Response response = vkService.execute(request)) {
            JSONObject jsonUser = (JSONObject) new JSONObject(response.getBody()).getJSONArray("response").get(0);
            user.setAuthProvider("vk");
            user.setIdProvider(jsonUser.getString("id"));
            user.setFirstName(jsonUser.getString("first_name"));
            user.setLastName(jsonUser.getString("last_name"));
            user.setUsername(((VKOAuth2AccessToken) accessToken).getEmail());
            user.setEmail(((VKOAuth2AccessToken) accessToken).getEmail());
            roleService.getRoleByName("USER").ifPresent(user::addRole);
        }
        return user;
    }

}
