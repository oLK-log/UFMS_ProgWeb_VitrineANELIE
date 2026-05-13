package com.Anelie.vitrineVirtual.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProdutoController {
    @GetMapping("/")
    public String exibirVitrine(){
        return "vitrine";
    }
    @GetMapping("/admin/cadastro")
    public String exibirCadastro(){
        return "cadastro_produto";
    }
}
