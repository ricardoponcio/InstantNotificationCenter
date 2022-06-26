package dev.poncio.SystemApps.InstantNotificationCenter.interfaces;

import javax.servlet.http.HttpServletRequest;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;

public interface RequestService {
    
    String getClientIp(HttpServletRequest request);

    Browser getBrowser(HttpServletRequest request);

    String getBrowserName(HttpServletRequest request);

    String getBrowserVersion(HttpServletRequest request);

    OperatingSystem getOperatingSystem(HttpServletRequest request);

    String getOperatingSystemName(HttpServletRequest request);

    String getDeviceType(HttpServletRequest request);

}
