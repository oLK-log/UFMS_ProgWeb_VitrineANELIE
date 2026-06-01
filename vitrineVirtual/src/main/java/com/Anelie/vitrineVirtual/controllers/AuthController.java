package com.Anelie.vitrineVirtual.controllers;

import com.Anelie.vitrineVirtual.dto.LoginRequestDTO;
import com.Anelie.vitrineVirtual.dto.RedefinirSenhaDTO;
import com.Anelie.vitrineVirtual.dto.SolicitarRecuperacaoDTO;
import com.Anelie.vitrineVirtual.models.Usuario;
import com.Anelie.vitrineVirtual.repositories.UsuarioRepository;
import com.Anelie.vitrineVirtual.security.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // Constante Global

    public static final String COOKIE_NAME = "jwt_token";

    // 2. Variáveis imutáveis (final)
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // 3. Injeção de Dependência via Construtor (Padrão Ouro do Spring)
    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequestDTO request, HttpServletResponse response) {
        // Autentica as credenciais
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.senha())
        );

        // Se correto, pega o usuário e gera o token
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtService.gerarToken(user);

        // Cria o Cookie usando a constante global
        Cookie jwtCookie = new Cookie(COOKIE_NAME, token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);  // Em produção (com HTTPS), mude para 'true'.
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60 * 24);

        // Lança o cookie na resposta e retorna apenas Status 200 OK
        response.addCookie(jwtCookie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/solicitar-recuperacao")
    public ResponseEntity<Void> solicitarRecuperacao(@RequestBody SolicitarRecuperacaoDTO request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByLogin(request.email());

        if (usuarioOpt.isPresent()) {
            String token = jwtService.gerarTokenRecuperacao(request.email());
            String linkDeRecuperacao = "http://localhost:8080/redefinir-senha?token=" + token;

            System.out.println("\n=======================================================");
            System.out.println(" SIMULAÇÃO DE ENVIO DE E-MAIL (MOCK)");
            System.out.println(" Para: " + request.email());
            System.out.println(" Assunto: Recuperação de Senha - Vitrine ANELIE");
            System.out.println(" Link Seguro: " + linkDeRecuperacao);
            System.out.println("=======================================================\n");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/salvar-nova-senha")
    public ResponseEntity<Void> salvarNovaSenha(@RequestBody RedefinirSenhaDTO request) {
        try {
            String email = jwtService.extrairUsername(request.token());
            Optional<Usuario> usuarioOpt = usuarioRepository.findByLogin(email);

            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                usuario.setSenha(passwordEncoder.encode(request.novaSenha()));
                usuarioRepository.save(usuario);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.badRequest().build();
    }
}