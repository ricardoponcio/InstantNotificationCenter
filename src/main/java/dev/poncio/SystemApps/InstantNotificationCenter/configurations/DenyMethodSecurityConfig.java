package dev.poncio.SystemApps.InstantNotificationCenter.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import dev.poncio.SystemApps.InstantNotificationCenter.filters.CustomPermissionDenySDKAccess;

@Configuration
public class DenyMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        return new CustomPermissionDenySDKAccess();
    }

}