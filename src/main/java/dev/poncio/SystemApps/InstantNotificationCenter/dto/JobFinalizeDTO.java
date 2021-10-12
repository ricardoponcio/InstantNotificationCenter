package dev.poncio.SystemApps.InstantNotificationCenter.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobFinalizeDTO implements Serializable {
    
    private boolean success;
    private String resultMessage;

}
