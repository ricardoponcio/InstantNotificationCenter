package dev.poncio.SystemApps.InstantNotificationCenter.dto;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class SecretUseDTO {
    
    private Long secretUseId;

}
