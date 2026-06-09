package com.Anelie.vitrineVirtual.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configure(http))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // Rotas Públicas
                        .requestMatchers(
                                "/", "/error",
                                "/produto/**", "/produtos/**",
                                "/carrinho", "/carrinho/**",
                                "/checkout", "/checkout/**",
                                "/pedido-finalizado",
                                "/api/pedidos/**",
                                "/auth/**",
                                "/login", "/recuperar-senha", "/redefinir-senha",
                                "/css/**", "/js/**", "/img/**",
                                "/*.jpg", "/*.jpeg", "/*.png", "/*.gif", "/*.webp",
                                "/static/**", "/images/**", "/uploads/**")
                        .permitAll()

                        // Rotas Privadas
                        .requestMatchers("/admin/**").authenticated()

                        // Para testes locais — libera o restante
                        .anyRequest().permitAll()

                ).addFilterBefore(jwtAuthFilter,
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public org.springframework.security.authentication.AuthenticationManager authenticationManager(
            org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }
}