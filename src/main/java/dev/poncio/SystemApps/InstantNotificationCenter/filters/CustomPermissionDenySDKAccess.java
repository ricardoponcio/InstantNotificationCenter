package dev.poncio.SystemApps.InstantNotificationCenter.filters;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.annotation.Jsr250SecurityConfig;
import org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource;
import org.springframework.stereotype.Controller;

import dev.poncio.SystemApps.InstantNotificationCenter.annotations.EnableSdkAccess;
import dev.poncio.SystemApps.InstantNotificationCenter.utils.AuthUtil;

public class CustomPermissionDenySDKAccess extends AbstractFallbackMethodSecurityMetadataSource {

    @Override
    protected Collection<ConfigAttribute> findAttributes(Class<?> clazz) {
        return null;
    }

    @Override
    protected Collection<ConfigAttribute> findAttributes(Method method, Class<?> targetClass) {
        List<ConfigAttribute> attributes = new ArrayList<>();

        if (AuthUtil.isSdkRequest() != null && AuthUtil.isSdkRequest().booleanValue()) {
            // if the class is annotated as @Controller we should by default deny access to
            // all methods
            if (AnnotationUtils.findAnnotation(targetClass, Controller.class) != null) {
                attributes.add(Jsr250SecurityConfig.DENY_ALL_ATTRIBUTE);
            }
            if (AnnotationUtils.findAnnotation(method, EnableSdkAccess.class) != null) {
                return null;
            }
        }

        return attributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

}