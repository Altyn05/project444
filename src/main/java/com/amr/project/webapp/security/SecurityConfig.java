package com.amr.project.webapp.security;

import com.amr.project.webapp.security.handler.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
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
    
    public SecurityConfig(UserDetailsService userDetailsService, LoginSuccessHandler loginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
        ;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/sign").anonymous()
                .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/adminapi/**").hasAuthority("ADMIN")
                
                .and()
                .formLogin()
                .loginPage("/")
                .successHandler(loginSuccessHandler)
                .loginProcessingUrl("/")
                .usernameParameter("username")
                .passwordParameter("password")
                
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
}
