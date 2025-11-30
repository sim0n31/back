package com.upc.molinachirinostp.security.config;

import com.upc.molinachirinostp.security.filters.AuthTokenFilter;
import com.upc.molinachirinostp.security.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableMethodSecurity // opcional, por si usas @PreAuthorize
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService; // mantiene tu inyección (Boot lo detecta)
    private final AuthTokenFilter authTokenFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, 
                         AuthTokenFilter authTokenFilter,
                         CorsConfigurationSource corsConfigurationSource) {
        this.userDetailsService = userDetailsService;
        this.authTokenFilter = authTokenFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/actuator/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                );

        // Filtro JWT antes del UsernamePasswordAuthenticationFilter
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Si en tu AuthController necesitas AuthenticationManager, expón este bean:
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager(); // usa el DaoAuthenticationProvider que Boot arma con tu UserDetailsService + PasswordEncoder
    }
}
