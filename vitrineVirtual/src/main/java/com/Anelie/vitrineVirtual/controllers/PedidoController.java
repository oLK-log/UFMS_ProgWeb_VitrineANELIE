package com.Anelie.vitrineVirtual.controllers;

import com.Anelie.vitrineVirtual.dto.CheckoutRequestDTO;
import com.Anelie.vitrineVirtual.models.ItemPedido;
import com.Anelie.vitrineVirtual.models.Pedido;
import com.Anelie.vitrineVirtual.models.Produto;
import com.Anelie.vitrineVirtual.repositories.PedidoRepository;
import com.Anelie.vitrineVirtual.repositories.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping("/finalizar")
    @Transactional // Garante que, se der erro em um item, o pedido inteiro sofre rollback
    public ResponseEntity<Void> finalizarPedido(@RequestBody CheckoutRequestDTO request) {

        Pedido pedido = new Pedido();
        pedido.setNomeCliente(request.cliente().nome());
        pedido.setEmailCliente(request.cliente().email());
        pedido.setTelefoneCliente(request.cliente().telefone());

        // Tratamento do endereço
        if ("entrega".equals(request.entrega().modo())) {
            String enderecoCompleto = request.entrega().endereco() + ", " +
                    request.entrega().cidade() + " - CEP: " +
                    request.entrega().cep();
            pedido.setEnderecoCliente(enderecoCompleto);
        } else {
            pedido.setEnderecoCliente("Retirada na Loja");
        }

        BigDecimal valorTotal = BigDecimal.ZERO;

        // Processamento dos itens
        for (CheckoutRequestDTO.ItemCheckoutDTO itemDto : request.itens()) {
            // Busca o produto real no banco de dados
            Produto produto = produtoRepository.findById(itemDto.id())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + itemDto.id()));

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDto.quantidade());
            item.setPrecoUnitario(produto.getValor()); // Regra de Ouro: o preço real vem do banco, não do front!

            pedido.adicionaItem(item); // Aquele método que construímos para amarrar o item ao pedido

            // Soma o total do pedido
            BigDecimal subtotal = produto.getValor().multiply(BigDecimal.valueOf(itemDto.quantidade()));
            valorTotal = valorTotal.add(subtotal);
        }

        pedido.setValorTotal(valorTotal);

        // O CascadeType.ALL na entidade Pedido fará o Hibernate salvar os itens automaticamente
        pedidoRepository.save(pedido);

        return ResponseEntity.ok().build();
    }
}