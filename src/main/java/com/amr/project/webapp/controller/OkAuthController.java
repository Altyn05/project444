package com.amr.project.webapp.controller;

import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.RoleService;
import com.amr.project.service.abstracts.UserService;
import com.github.openjson.JSONObject;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
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
@RequestMapping("/oauth2/ok")
public class OkAuthController {

    private final OAuth20Service okService;
    private final UserService userService;
    private final RoleService roleService;
    private final Environment env;

    @Autowired
    public OkAuthController(@Qualifier("okOAuthService") OAuth20Service okService,
                            UserService userService, RoleService roleService, Environment env) {
        this.okService = okService;
        this.userService = userService;
        this.roleService = roleService;
        this.env = env;
    }

    @GetMapping
    public String openLoginForm() {
        return "redirect:" + okService.getAuthorizationUrl();
    }

    @GetMapping("/code")
    public String processOAuth2User(@RequestParam(value = "code", required = false) String code) throws Exception {

        OAuth2AccessToken accessToken = okService.getAccessToken(code);

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

        return "redirect:/";
    }

    private void authenticate(User user) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    private User getUserByToken(OAuth2AccessToken accessToken) throws IOException, ExecutionException, InterruptedException {
        User user = new User();

        final OAuthRequest request = new OAuthRequest(Verb.GET, String.format(
                "https://api.ok.ru/api/users/getCurrentUser?application_key=%1$s&format=JSON",
                env.getProperty("ok.client-public"))
        );

        okService.signRequest(accessToken, request);
        try (Response response = okService.execute(request)) {
            JSONObject jsonUser = new JSONObject(response.getBody());
            user.setAuthProvider("ok");
            user.setIdProvider(jsonUser.getString("uid"));
            user.setFirstName(jsonUser.getString("first_name"));
            user.setLastName(jsonUser.getString("last_name"));
            user.setUsername(jsonUser.getString("uid"));
            user.setAge(jsonUser.getInt("age"));
            user.setEmail(jsonUser.getString("uid"));
            roleService.getRoleByName("USER").ifPresent(user::addRole);
        }

        return user;
    }

}
