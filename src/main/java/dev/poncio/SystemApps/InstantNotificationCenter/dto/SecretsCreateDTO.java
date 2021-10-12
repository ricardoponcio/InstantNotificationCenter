package dev.poncio.SystemApps.InstantNotificationCenter.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecretsCreateDTO implements Serializable {
    
    private String name;
    private String description;

}
