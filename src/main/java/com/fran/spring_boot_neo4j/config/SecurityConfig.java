package com.fran.spring_boot_neo4j.config;

import com.fran.spring_boot_neo4j.services.NeoUserDetailsService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Security configuration for the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // See application.properties
    @Value("${app.cors.allowed-origins}")
    private List<String> allowedOrigins;

    private final NeoUserDetailsService neoUserDetailsService;

    /**
     * Constructs a new {@code SecurityConfig} with the specified {@code NeoUserDetailsService}.
     *
     * @param neoUserDetailsService the service to manage user details
     */
    public SecurityConfig(NeoUserDetailsService neoUserDetailsService) {
        this.neoUserDetailsService = neoUserDetailsService;
    }

    /**
     * Configures the security filter chain.
     *
     * @param httpSecurity the {@link HttpSecurity} to modify
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            // Set session creation policy to stateless (suitable for REST APIs)
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Disable CSRF protection (not typically needed for stateless REST APIs)
            .csrf(AbstractHttpConfigurer::disable)
            // Enable CORS with default settings
            .cors(Customizer.withDefaults())
            // Secure specific endpoints and permit others
            // These endpoints need to be authenticated (logged in)
            .authorizeHttpRequests(
                auth -> auth.requestMatchers(
                        "api/v1/auth/me",
                        "api/v1/enrollments/**"
                    ).authenticated().anyRequest()
                    .permitAll())
            // Configure the custom NeoUserDetailsService for loading user-specific data
            .userDetailsService(neoUserDetailsService)
            // Enable HTTP Basic authentication with default settings
            .httpBasic(Customizer.withDefaults()).build();
    }

    /**
     * Configures the CORS settings for the application.
     *
     * @return the configured {@link CorsConfigurationSource}
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Set allowed origins for CORS requests from environment file
        // The allowed origins are set to http:
        // localhost:3000 and http://127.0.0.1:3000 as placeholders,
        // assuming that your frontend application might be running on port 3000
        corsConfiguration.setAllowedOrigins(allowedOrigins);

        // Set allowed HTTP methods for CORS requests
        corsConfiguration.setAllowedMethods(
            Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD"));

        // Allow credentials in CORS requests
        corsConfiguration.setAllowCredentials(true);

        // Set allowed headers for CORS requests
        corsConfiguration.setAllowedHeaders(
            Arrays.asList("Authorization", "Request-Type", "Content-Type"));

        // Set headers that can be exposed in the response
        corsConfiguration.setExposedHeaders(Arrays.asList("X-Get-Header"));

        // Set the maximum age for the CORS configuration to be cached by clients
        corsConfiguration.setMaxAge(3600L);

        // Register the CORS configuration for all paths
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }

    /**
     * Provides a {@link PasswordEncoder} bean that uses the BCrypt hashing function.
     *
     * @return a {@link PasswordEncoder} that uses BCrypt for hashing passwords
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
