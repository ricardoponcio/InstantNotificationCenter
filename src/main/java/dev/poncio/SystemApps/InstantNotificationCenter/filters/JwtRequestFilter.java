package dev.poncio.SystemApps.InstantNotificationCenter.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.poncio.SystemApps.InstantNotificationCenter.entities.Secrets;
import dev.poncio.SystemApps.InstantNotificationCenter.services.SecretsService;
import dev.poncio.SystemApps.InstantNotificationCenter.services.UserService;
import dev.poncio.SystemApps.InstantNotificationCenter.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService jwtUserDetailsService;

	@Autowired
	private SecretsService secretsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		response.setHeader("X-Frame-Options", "SAMEORIGIN");

		final String requestTokenHeader = request.getHeader("Authorization");
		if (requestTokenHeader != null && !requestTokenHeader.trim().isEmpty()) {
			processBearerView(request, requestTokenHeader);
		}

		final String requestTokenSdk = request.getHeader("SDK-Auth");
		if (requestTokenSdk != null && !requestTokenSdk.trim().isEmpty()) {
			processTokenSdk(request, requestTokenSdk);
		}

		chain.doFilter(request, response);
	}

	private void processBearerView(HttpServletRequest request, String authTokenBearer) {
		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		logger.debug("JWT Received: " + authTokenBearer);
		if (authTokenBearer != null && authTokenBearer.startsWith("Bearer ")) {
			jwtToken = authTokenBearer.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				logger.debug("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				logger.debug("JWT Token has expired");
			}
		} else {
			logger.warn("URL: " + JwtRequestFilter.getURL(request));
			logger.warn("JWT Token does not begin with Bearer String");
			// logger.warn(HttpUtils.getInstance().getFullURL(request));
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set
			// authentication

			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
	}

	private void processTokenSdk(HttpServletRequest request, String authTokenSdk) {
		Secrets validToken = this.secretsService.findValidToken(authTokenSdk);
		if (validToken != null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(validToken.getUser().getEmail());
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, validToken, Arrays.asList("ROLE_SDK").stream()
							.map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
	}

	private static String getURL(HttpServletRequest req) {

		String scheme = req.getScheme(); // http
		String serverName = req.getServerName(); // hostname.com
		int serverPort = req.getServerPort(); // 80
		String contextPath = req.getContextPath(); // /mywebapp
		String servletPath = req.getServletPath(); // /servlet/MyServlet
		String pathInfo = req.getPathInfo(); // /a/b;c=123
		String queryString = req.getQueryString(); // d=789

		// Reconstruct original requesting URL
		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);

		if (serverPort != 80 && serverPort != 443) {
			url.append(":").append(serverPort);
		}

		url.append(contextPath).append(servletPath);

		if (pathInfo != null) {
			url.append(pathInfo);
		}
		if (queryString != null) {
			url.append("?").append(queryString);
		}
		return url.toString();
	}

}