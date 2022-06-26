package dev.poncio.SystemApps.InstantNotificationCenter.filters;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import dev.poncio.SystemApps.InstantNotificationCenter.annotations.EnableSdkAccess;
import dev.poncio.SystemApps.InstantNotificationCenter.dto.SecretUseDTO;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.SecretUse;
import dev.poncio.SystemApps.InstantNotificationCenter.exceptions.SDKNotAllowedException;
import dev.poncio.SystemApps.InstantNotificationCenter.services.SecretsService;
import dev.poncio.SystemApps.InstantNotificationCenter.utils.AuthUtil;

@Aspect
@Component
public class SDKAuthorizeFilterAspect {

    @Autowired
    private SecretsService secretsService;

    @Pointcut("execution(public * dev.poncio.SystemApps.InstantNotificationCenter.controllers..*(..))")
    public void allControllerMethods() {

    }

    @Around("allControllerMethods()")
    public Object checkAccess(ProceedingJoinPoint pjp) throws Exception {
        Object retObject = null;
        Method method = getMethod(pjp);
        boolean metodoAcessivel = AnnotationUtils.findAnnotation(method, EnableSdkAccess.class) != null;

        if (AuthUtil.isSdkRequest()) {
            SecretUseDTO secretUseDTO = AuthUtil.get().getSecretUseDTO();
            if (secretUseDTO != null) {
                SecretUse secretUse = this.secretsService.findUseById(secretUseDTO.getSecretUseId());
                secretUse.setFilterProceed(metodoAcessivel);
                this.secretsService.persistSecretUse(secretUse);
            }
        }

        if (!AuthUtil.isSdkRequest() || metodoAcessivel) {
            try {
                retObject = pjp.proceed();
            } catch (Throwable e) {
                // Handle the exception
            }
        } else {
            throw new SDKNotAllowedException();
        }
        return retObject;
    }

    // Refer : https://stackoverflow.com/q/5714411/4214241
    private Method getMethod(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = pjp.getTarget().getClass().getDeclaredMethod(pjp.getSignature().getName(),
                        method.getParameterTypes());
            } catch (final SecurityException exception) {
                // ...
            } catch (final NoSuchMethodException exception) {
                // ...
            }
        }
        return method;
    }

}