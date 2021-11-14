package com.amr.project.oauth2;

import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.RoleService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserProcessingService {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserProcessingService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public void process(User newUser, User existingUser, Boolean authenticate) {

        if (existingUser != null) {
            existingUser.setAuthProvider(newUser.getAuthProvider());
            existingUser.setIdProvider(newUser.getIdProvider());
            userService.update(existingUser);
            if (authenticate) {
                authenticate(existingUser);
            }
        } else {
            newUser.addRole(roleService.getRoleByName("USER"));
            userService.persist(newUser);
            if (authenticate) {
                authenticate(newUser);
            }
        }

    }

    private void authenticate(User user) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

}
