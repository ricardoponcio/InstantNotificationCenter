package dev.poncio.SystemApps.InstantNotificationCenter.filters;

import javax.inject.Inject;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import dev.poncio.SystemApps.InstantNotificationCenter.services.WebSocketAuthenticatorService;

@Component
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {
    
    private static final String USER_TOKEN_HEADER = "userToken";
    private static final String STOMP_TOKEN_HEADER = "stompToken";

    private final WebSocketAuthenticatorService webSocketAuthenticatorService;

    @Inject
    public AuthChannelInterceptorAdapter(final WebSocketAuthenticatorService webSocketAuthenticatorService) {
        this.webSocketAuthenticatorService = webSocketAuthenticatorService;
    }

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            final String userToken = accessor.getFirstNativeHeader(USER_TOKEN_HEADER);
            final String stompToken = accessor.getFirstNativeHeader(STOMP_TOKEN_HEADER);

            final UsernamePasswordAuthenticationToken user = webSocketAuthenticatorService.getAuthenticatedOrFail(userToken, stompToken);

            accessor.setUser(user);
        }
        return message;
    }

}