package dev.poncio.SystemApps.InstantNotificationCenter.configurations;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import dev.poncio.SystemApps.InstantNotificationCenter.filters.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    private static final String[] AUTH_WHITELIST = {
        // -- swagger ui
        "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
        "/configuration/security", "/swagger-ui.html", "/webjars/*", "/webjars/**", "/v2/*", "/v2/**",
        "/swagger-ui.html", "swagger-ui.html",
        // other public endpoints of your API may be appended to this array

        // -- Actuator
        "/actuator", "/actuator/*", "/actuator/**", "/loggers", "/loggers/**", "/metrics", "/metrics/**", "/health",
        "/health/**",
        // other public endpoints of your API may be appended to this array

		// -- Frontend
		"/", "/home","/pictureCheckCode","/include/**",
		"/css/**","/icons/**","/images/**","/js/**","/layer/**",
		"/*.js", "/*.css", "/assets/**", "/*.ico",
		"/socket", "/socket/**",
		//

    };

	public static final String[] ALLOWED_ORIGINS = {
		"http://localhost:4200/", "http://localhost:4049/"
	};

    @Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

    @Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

    @Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
				.cors().and().

				// dont authenticate this particular request
				// Login & WS
				authorizeRequests().antMatchers("/user/login").permitAll().

				// Whitelist Swagger 2 and others
				antMatchers(AUTH_WHITELIST).permitAll().

				// all other requests need to be authenticated
				anyRequest().authenticated().and()

				// make sure we use stateless session; session won't be used to
				// store user's state.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				// Add a filter to validate the tokens with every request
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

    @Bean
	protected CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		List<String> allowedOrigins = Arrays.asList(WebSecurityConfig.ALLOWED_ORIGINS);
		configuration.setAllowedOrigins(allowedOrigins);
		configuration.setAllowedOriginPatterns(allowedOrigins);
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
		// setAllowCredentials(true) is important, otherwise:
		// The value of the 'Access-Control-Allow-Origin' header in the response must
		// not be the wildcard '*' when the request's credentials mode is 'include'.
		configuration.setAllowCredentials(true);
		// setAllowedHeaders is important! Without it, OPTIONS preflight request
		// will fail with 403 Invalid CORS request
		configuration
				.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "X-Frame-Options"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
