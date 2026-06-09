package com.Anelie.vitrineVirtual.dto;

import java.io.Serializable;
import java.math.BigDecimal;

// representa um item armazenado no carrinho de compras
public record ItemCarrinhoDTO(Long produtoId, String nome, BigDecimal precoUnitario, int quantidade) implements Serializable {
    // metodo para calcular o valor total de um item no pedido
    public BigDecimal getSubtotal(){
        return this.precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
