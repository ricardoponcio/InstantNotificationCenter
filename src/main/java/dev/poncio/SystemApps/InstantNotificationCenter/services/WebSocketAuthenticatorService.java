package dev.poncio.SystemApps.InstantNotificationCenter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import dev.poncio.SystemApps.InstantNotificationCenter.utils.JwtTokenUtil;

@Component
public class WebSocketAuthenticatorService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    // This method MUST return a UsernamePasswordAuthenticationToken instance, the spring security chain is testing it with 'instanceof' later on. So don't use a subclass of it or any other class
    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String  userToken, final String stompToken) throws AuthenticationException {
        if (userToken == null || userToken.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("User Token was null or empty.");
        }
        if (stompToken == null || stompToken.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("STOMP Token was null or empty.");
        }

        // Try decrypt token and get the user
        String authUsername = null;
        try {
            authUsername = this.jwtTokenUtil.getUsernameFromToken(userToken);
            if (authUsername == null || authUsername.trim().isEmpty()) {
                throw new AuthenticationCredentialsNotFoundException("Token provided doesn't represents an user");
            }
        } catch(Exception e) {
            throw new BadCredentialsException("Token provided in wrong way", e);
        }

        // Confirm that the username extracted from token exists
        UserDetails authUser = userService.loadUserByUsername(authUsername);
        if (authUser == null) {
            throw new BadCredentialsException("Bad credentials for user " + authUsername);
        }

        // TODO - Check also STOMP Token
        if (stompToken == null || stompToken.trim().isEmpty()) {
            throw new BadCredentialsException("Not found STOMP Token Registered!");
        }

        // null credentials, we do not pass the password along
        return new UsernamePasswordAuthenticationToken(
            authUser.getUsername(),
            authUser.getPassword(),
            authUser.getAuthorities()
        );
    }
}