package dev.poncio.SystemApps.InstantNotificationCenter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class SDKNotAllowedException extends RuntimeException {

    public SDKNotAllowedException() {
        super("SDK Requests are not allowed to access this endpoint!");
    }

}
