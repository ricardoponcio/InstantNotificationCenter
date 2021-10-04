package dev.poncio.SystemApps.InstantNotificationCenter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDTO {
    
    private String token;
    @JsonProperty("expires_in")
    private Long expiresIn;

}
