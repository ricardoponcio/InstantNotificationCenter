package dev.poncio.SystemApps.InstantNotificationCenter.services;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import dev.poncio.SystemApps.InstantNotificationCenter.interfaces.RequestService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

@Service
public class RequestServiceImpl implements RequestService {

    private final String LOCALHOST_IPV4 = "127.0.0.1";
    private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    private UserAgent parseUserAgent(HttpServletRequest request) {
        return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    }

    @Override
    public String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (Strings.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (Strings.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (Strings.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ipAddress = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!Strings.isBlank(ipAddress)
                && ipAddress.length() > 15
                && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }

    @Override
    public Browser getBrowser(HttpServletRequest request) {
        UserAgent userAgent = parseUserAgent(request);
        return userAgent != null ? userAgent.getBrowser() : null;
    }

    @Override
    public String getBrowserName(HttpServletRequest request) {
        Browser browser = getBrowser(request);
        return browser != null ? browser.getName() : null;
    }

    @Override
    public String getBrowserVersion(HttpServletRequest request) {
        UserAgent userAgent = parseUserAgent(request);
        return userAgent != null && userAgent.getBrowserVersion() != null ? userAgent.getBrowserVersion().getVersion()
                : null;
    }

    @Override
    public OperatingSystem getOperatingSystem(HttpServletRequest request) {
        UserAgent userAgent = parseUserAgent(request);
        return userAgent != null ? userAgent.getOperatingSystem() : null;
    }

    @Override
    public String getOperatingSystemName(HttpServletRequest request) {
        OperatingSystem operatingSystem = getOperatingSystem(request);
        return operatingSystem != null ? operatingSystem.getName() : null;
    }

    @Override
    public String getDeviceType(HttpServletRequest request) {
        OperatingSystem operatingSystem = getOperatingSystem(request);
        return operatingSystem != null && operatingSystem.getDeviceType() != null
                ? operatingSystem.getDeviceType().getName()
                : null;
    }

}
