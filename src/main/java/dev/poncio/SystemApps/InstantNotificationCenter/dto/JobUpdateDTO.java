package dev.poncio.SystemApps.InstantNotificationCenter.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobUpdateDTO implements Serializable {
    
    private Integer percent;
    private String log;

}
