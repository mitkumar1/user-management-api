package com.kumar.wipro.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kumar.wipro.api.security.JwtAuthenticationEntryPoint;
import com.kumar.wipro.api.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authz -> authz
<<<<<<< HEAD
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/api/users/register").permitAll()
                    .requestMatchers("/api/v1/users/register").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/users/{username}").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/users/{username}").permitAll()
                    .requestMatchers("/actuator/**").permitAll()
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/error").permitAll()
                    .anyRequest().authenticated()
                )
                .headers(headers -> headers
                    .frameOptions(frameOptions -> frameOptions.disable())
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
=======
                    // PUBLIC ENDPOINTS - No authentication required (INTERVIEW SOLUTION)
                    .requestMatchers("/api/auth/**").permitAll()           // All auth endpoints
                    .requestMatchers("/api/auth/login").permitAll()        // Login endpoint
                    .requestMatchers("/api/submit/**").permitAll()         // Submit button endpoints
                    .requestMatchers("/api/public/**").permitAll()         // Public APIs
                    .requestMatchers("/h2-console/**").permitAll()         // H2 console
                    .requestMatchers("/api/users/register").permitAll()    // Registration
                    .requestMatchers(HttpMethod.GET, "/api/users/{username}").permitAll()
                    .requestMatchers("/actuator/**").permitAll()           // Health check
                    .requestMatchers("/swagger-ui/**").permitAll()         // Swagger UI
                    .requestMatchers("/v3/api-docs/**").permitAll()        // API docs
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/error").permitAll()
                    
                    // OPTION 1: Make specific endpoints passwordless
                    .requestMatchers("/api/passwordless/**").permitAll()   // Passwordless endpoints
                    
                    // OPTION 2: Make ALL endpoints passwordless (uncomment below line)
                    // .anyRequest().permitAll()                           // Allow ALL without authentication
                    
                    // OPTION 3: Keep some endpoints protected (current configuration)
                    .anyRequest().authenticated()                          // Other endpoints need auth
                );

        // Fix H2 console frame options (Updated for Spring Security 6.1+)
        http.headers(headers -> headers.frameOptions().sameOrigin());

        // Comment these lines to completely disable JWT authentication
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
>>>>>>> 412a126dbe54f91274aa9b3d23670d15ef4326aa
    }

    /*
     * INTERVIEW QUESTION SOLUTION:
     * "How to make Spring Boot endpoints passwordless and accessible to everyone?"
     * 
     * APPROACH 1: Selective Passwordless Access (RECOMMENDED)
     * - Use .permitAll() for specific endpoints like login, submit
     * - Keep sensitive endpoints protected with .authenticated()
     * 
     * APPROACH 2: Complete Passwordless Application
     * - Replace .anyRequest().authenticated() with .anyRequest().permitAll()
     * - Comment out JWT filter and authentication provider
     * 
     * APPROACH 3: Disable Security Completely
     * - Add in application.properties:
     *   spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
     * 
     * TESTING:
     * - Test without Authorization header: curl -X POST http://localhost:8080/api/auth/login
     * - Should return 200 OK instead of 401 Unauthorized
     * 
     * SECURITY CONSIDERATIONS:
     * - Never make admin endpoints public in production
     * - Implement rate limiting for public endpoints
     * - Validate all inputs even for public endpoints
     * - Use HTTPS in production
     * - Monitor access logs for suspicious activity
     */
}
