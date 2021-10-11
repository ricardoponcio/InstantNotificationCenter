package dev.poncio.SystemApps.InstantNotificationCenter.configurations;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import dev.poncio.SystemApps.InstantNotificationCenter.filters.AuthChannelInterceptorAdapter;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    
    @Inject
    private AuthChannelInterceptorAdapter authChannelInterceptorAdapter;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket")
            .setAllowedOrigins(WebSecurityConfig.ALLOWED_ORIGINS)
            .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
            .enableSimpleBroker("/job/update");
    }

    @Override
    public void configureClientInboundChannel(final ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptorAdapter);
    }

}
