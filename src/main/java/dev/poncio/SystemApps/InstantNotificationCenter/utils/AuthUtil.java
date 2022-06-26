package dev.poncio.SystemApps.InstantNotificationCenter.utils;

import javax.inject.Inject;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import dev.poncio.SystemApps.InstantNotificationCenter.dto.SecretUseDTO;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.Secrets;
import lombok.Getter;
import lombok.Setter;

public class AuthUtil {

    private static AuthUtil instance;

    @Inject
    @Getter
    @Setter
    private SecretUseDTO secretUseDTO;

    private AuthUtil() {

    }

    public static AuthUtil get() {
        if (instance == null)
            instance = new AuthUtil();
        return instance;
    }

    public static UserDetails contextAuthUser() {
        UsernamePasswordAuthenticationToken authUser = AuthUtil.getContextAuth();
        return authUser == null ? null : (UserDetails) authUser.getPrincipal();
    }

    public static Secrets contextAuthCredentials() {
        UsernamePasswordAuthenticationToken authUser = AuthUtil.getContextAuth();
        return authUser == null ? null : (Secrets) authUser.getCredentials();
    }

    public static boolean isSdkRequest() {
        UsernamePasswordAuthenticationToken authUser = AuthUtil.getContextAuth();
        return authUser == null ? false
                : authUser.getAuthorities().stream().anyMatch(authority -> "ROLE_SDK".equals(authority.getAuthority()));
    }

    private static UsernamePasswordAuthenticationToken getContextAuth() {
        Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth instanceof UsernamePasswordAuthenticationToken))
            return null;
        return (UsernamePasswordAuthenticationToken) auth;
    }

}
