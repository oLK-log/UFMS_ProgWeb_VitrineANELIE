package com.Anelie.vitrineVirtual.dto;

import java.math.BigDecimal;

public record ProdutoDetalhadoDTO(
        Long id,
        String nome,
        String categoria,
        String descricao,
        String material,
        String imagem,
        BigDecimal valor
) {}