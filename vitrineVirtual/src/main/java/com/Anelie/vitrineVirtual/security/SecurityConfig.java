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
                // Desabilita CSRF (segurança será via JWT (Stateless))
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configure(http))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Aqui definimos quem entra onde
                .authorizeHttpRequests(auth -> auth
                        // Rota Públicas(O Cliente acessando o catálogo)
                        // aqui eh o que vamos permitir um user comum acessar
                        .requestMatchers("/", "/produto/**", "/produtos/**", "/carrinho/**", "/checkout", "/pedido-finalizado", "/api/pedidos/**", "/auth/**", "/login", "/recuperar-senha", "/redefinir-senha", "/css/**", "/js/**", "/img/**", "/*.png", "/*.jpg").permitAll()

                        // Rotas Privadas, tipo Lojista fazendo o CRUD)
                        .requestMatchers("/admin/**").authenticated()

                        // Qualquer outra rota que não mapeamos, bloqueia por segurança
                        .anyRequest().authenticated()

                ).addFilterBefore(jwtAuthFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // usaando o BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public org.springframework.security.authentication.AuthenticationManager authenticationManager(
            org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
