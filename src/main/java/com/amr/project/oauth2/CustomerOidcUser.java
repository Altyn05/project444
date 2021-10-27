package com.amr.project.oauth2;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class CustomerOidcUser extends DefaultOidcUser {

    private OidcUser oidcUser;

    public CustomerOidcUser(OidcUser oidcUser) {
        super(oidcUser.getAuthorities(), oidcUser.getIdToken());
        this.oidcUser = oidcUser;
    }

    @Override
    public String getName() {
        return oidcUser.getFullName();
    }
}
