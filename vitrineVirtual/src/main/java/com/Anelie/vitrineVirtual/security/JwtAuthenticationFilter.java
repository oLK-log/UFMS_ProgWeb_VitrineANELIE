package com.Anelie.vitrineVirtual.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // autorizacao da requisicao
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        //verifica se cabecalho ou beer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // pega somente token
        jwt = authHeader.substring(7);
        userEmail = jwtService.extrairUsername(jwt);
        // Se tiver um e-mail no token e o usuário n está autenticado no contexto do Spring
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Vai no banco de dados buscar o Lojista
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // se valido e n expirado
            if (jwtService.isTokenValido(jwt, userDetails)) {
                // cria credencia spring security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // login propriamente dito
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
