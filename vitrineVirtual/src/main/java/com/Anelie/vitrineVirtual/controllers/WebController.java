package com.Anelie.vitrineVirtual.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class WebController {

    @GetMapping("/login")
    public String telaLogin() {
        return "login";
    }
    @GetMapping("/recuperar-senha")
    public String telaRecuperarSenha() {
        return "recuperar_senha"; // Busca recuperar_senha.html
    }

    @GetMapping("/redefinir-senha")
    public String telaRedefinirSenha(@RequestParam(name = "token", required = false) String token, Model model) {
        // Captura o ?token= da URL e envia para o Thymeleaf embutir no HTML
        model.addAttribute("token", token);
        return "redefinir_senha"; // Busca redefinir_senha.html
    }
}
