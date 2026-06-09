package com.Anelie.vitrineVirtual.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PedidoController {

    @GetMapping("/carrinho")
    public String exibirCarrinho() {
        return "carrinho";
    }

    @GetMapping("/checkout")
    public String exibirCheckout() {
        return "checkout";
    }

    @GetMapping("/pedido-finalizado")
    public String exibirPedidoFinalizado() {
        return "pedido_finalizado";
    }
}