package com.amr.project.webapp.security;

import com.amr.project.oauth2.CustomOAuth2UserService;
import com.amr.project.oauth2.CustomOidcUserService;
import com.amr.project.webapp.security.handler.LoginSuccessHandler;
import com.github.scribejava.apis.OdnoklassnikiApi;
import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;
    private final CustomOAuth2UserService oauth2UserService;
    private final CustomOidcUserService oidcUserService;
    private final Environment env;


    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          LoginSuccessHandler loginSuccessHandler,
                          CustomOAuth2UserService oauth2UserService,
                          CustomOidcUserService oidcUserService,
                          Environment env) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.oauth2UserService = oauth2UserService;
        this.oidcUserService = oidcUserService;
        this.env = env;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                
                .and()
                .authorizeRequests()
                .antMatchers("/", "/error").permitAll()
                .antMatchers("/sign").anonymous()
                .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN", "ROLE_USER")
                .antMatchers("/update").anonymous()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/adminapi/**").hasAuthority("ADMIN")
                
                .and()
                .formLogin()
                .loginPage("/")
                .successHandler(loginSuccessHandler)
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")

                .and()
                .oauth2Login()
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                        .userService(oauth2UserService)
                        .oidcUserService(oidcUserService))

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                
                .and()
                .rememberMe();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public OAuth20Service vkOAuthService() {
        return new ServiceBuilder(env.getProperty("vk.client-id"))
                .apiSecret(env.getProperty("vk.client-secret"))
                .defaultScope(env.getProperty("vk.scope"))
                .callback(env.getProperty("vk.redirect-uri"))
                .build(VkontakteApi.instance());
    }

    @Bean
    public OAuth20Service okOAuthService() {
        return new ServiceBuilder(env.getProperty("ok.client-id"))
                .apiSecret(env.getProperty("ok.client-secret"))
                .defaultScope(env.getProperty("ok.scope"))
                .callback(env.getProperty("ok.redirect-uri"))
                .build(OdnoklassnikiApi.instance());
    }

}
