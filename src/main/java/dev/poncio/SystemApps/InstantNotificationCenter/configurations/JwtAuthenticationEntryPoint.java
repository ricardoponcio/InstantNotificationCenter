package dev.poncio.SystemApps.InstantNotificationCenter.configurations;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import dev.poncio.SystemApps.InstantNotificationCenter.services.MessageService;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    
    @Autowired
    private MessageService messageService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        out.print(
            new Gson().toJson(
                ResponseEntity.status(401)
                .eTag(messageService.get("error.unauthorized"))
                .build()
            )
        );
        out.flush();
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

}
