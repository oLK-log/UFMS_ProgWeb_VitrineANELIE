package com.Anelie.vitrineVirtual.controllers;

import com.Anelie.vitrineVirtual.models.Produto;
import com.Anelie.vitrineVirtual.repositories.ProdutoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class WebController {
    private final ProdutoRepository produtoRepository;

    public WebController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

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
        return "alterar_senha"; // Busca redefinir_senha.html
    }

    @GetMapping("/produto/{id}")
    public String detalhesProduto(@PathVariable("id") Long id, Model model) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + id));
        model.addAttribute("produto", produto);
        return "detalhes_produto";
    }

    @GetMapping("/carrinho")
    public String telaCarrinho() {
        return "carrinho";
    }

    @GetMapping("/checkout")
    public String telaCheckout() {
        return "checkout";
    }

    @GetMapping("/pedido-finalizado")
    public String telaPedidoFinalizado() {
        return "pedido_finalizado";
    }
}
