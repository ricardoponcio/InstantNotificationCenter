package dev.poncio.SystemApps.InstantNotificationCenter.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import dev.poncio.SystemApps.InstantNotificationCenter.entities.Secrets;

public class AuthUtil {

    public static UserDetails contextAuthUser() {
        UsernamePasswordAuthenticationToken authUser = AuthUtil.getContextAuth();
        return authUser == null ? null : (UserDetails) authUser.getPrincipal();
    }

    public static Secrets contextAuthCredentials() {
        UsernamePasswordAuthenticationToken authUser = AuthUtil.getContextAuth();
        return authUser == null ? null : (Secrets) authUser.getCredentials();
    }

    public static Boolean isSdkRequest() {
        UsernamePasswordAuthenticationToken authUser = AuthUtil.getContextAuth();
        return authUser == null ? null : authUser.getAuthorities().stream().anyMatch(authority -> "ROLE_SDK".equals(authority.getAuthority()));
    }

    private static UsernamePasswordAuthenticationToken getContextAuth() {
        Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            return null;
        return (UsernamePasswordAuthenticationToken) auth;
    }

}
