package com.pay.tracker.commons.service;

import com.pay.tracker.commons.model.ErrorException;
import com.pay.tracker.commons.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class UserSecurityContext {

    private UserSecurityContext() {
    }

    public static User getUser() {
        JwtAuthenticationToken authentication = getJwtAuthenticationToken();
        var claims = authentication.getToken().getClaims();
        var name = (String) claims.get("preferred_username");
        var id = (Long) claims.get("user-id");
        if (name == null || id == null) {
            throw new ErrorException("Invalid user info");
        } else return new User(id, name);
    }

    private static JwtAuthenticationToken getJwtAuthenticationToken() {
        JwtAuthenticationToken authentication;
        try {
            authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        } catch (Exception ex) {
            throw new ErrorException("Invalid token");
        }
        return authentication;
    }
}
