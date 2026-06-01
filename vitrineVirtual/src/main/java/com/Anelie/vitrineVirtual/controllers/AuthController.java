package com.Anelie.vitrineVirtual.controllers;

import com.Anelie.vitrineVirtual.dto.LoginRequestDTO;
import com.Anelie.vitrineVirtual.dto.LoginResponseDTO;
import com.Anelie.vitrineVirtual.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager; //validador de senha

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        // autenticar
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.senha())
        );

        // se correto, pega usuario
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtService.gerarToken(user);
        // lanca token pro front
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
