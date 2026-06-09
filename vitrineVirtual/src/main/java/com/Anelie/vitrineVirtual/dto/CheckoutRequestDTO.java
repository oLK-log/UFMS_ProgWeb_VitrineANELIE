package com.Anelie.vitrineVirtual.dto;

import java.util.List;

public record CheckoutRequestDTO(
        ClienteDTO cliente,
        EntregaDTO entrega,
        String pagamento,
        List<ItemCheckoutDTO> itens
) {
    public record ClienteDTO(String nome, String email, String telefone) {}

    public record EntregaDTO(String modo, String endereco, String cidade, String cep) {}

    public record ItemCheckoutDTO(Long id, Integer quantidade) {}
}