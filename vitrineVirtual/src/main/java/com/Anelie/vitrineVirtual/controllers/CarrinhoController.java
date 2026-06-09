package com.Anelie.vitrineVirtual.controllers;

import com.Anelie.vitrineVirtual.dto.ItemCarrinhoDTO;
import com.Anelie.vitrineVirtual.models.Produto;
import com.Anelie.vitrineVirtual.repositories.ProdutoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {
    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping("/add")
    public ResponseEntity<List<ItemCarrinhoDTO>> adicionarItem(@RequestParam Long produtoId, @RequestParam int quantidade, HttpSession session){

        // recupera a lista de records armazenadas na sessao
        List<ItemCarrinhoDTO> itens = (List<ItemCarrinhoDTO>) session.getAttribute("ITENS_CARRINHO");
        if (itens == null){
            itens = new ArrayList<>();
            session.setAttribute("ITENS_CARRINHO", itens);
        }

        // busca produto no banco de dados para garantir preços e dados atualizados
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        //verifica se o produto ja esta na lista do carrinho
        int indexExistente = -1;
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).produtoId().equals(produtoId)) {
                indexExistente = i;
                break;
            }
        }

        // verifica se o item ja existe na lista do carrinho
        if (indexExistente != -1) {
            // como o itemcarrinho foi criado como um record, é necessário recriar o objeto armazenado na lista toda vez que em for adicionar
            ItemCarrinhoDTO itemAntigo = itens.get(indexExistente);

            ItemCarrinhoDTO itemAtualizado = new ItemCarrinhoDTO(itemAntigo.produtoId(), itemAntigo.nome(), itemAntigo.precoUnitario(), itemAntigo.quantidade() + quantidade);

            itens.set(indexExistente, itemAtualizado);
        }else{
            // adiciona um novo item a lista do carrinho caso não exista ainda nela
            ItemCarrinhoDTO novoItem = new ItemCarrinhoDTO(produto.getId(), produto.getNome(), produto.getValor(), quantidade);
            itens.add(novoItem);
        }

        return ResponseEntity.ok(itens);
    }


    // remove da lista o item que possui o produtoId enviado, rodando um loop interno que apaga se a condição for verdadeira
    @PostMapping("/remover")
    public ResponseEntity<List<ItemCarrinhoDTO>> removerItem(@RequestParam Long produtoId, HttpSession session) {

            List<ItemCarrinhoDTO> itens = obterItensDaSessao(session);

            // remove do carrinho um item pelo id informado
            itens.removeIf(item -> item.produtoId().equals(produtoId));

            return ResponseEntity.ok(itens);
        }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> calcularValorTotal(HttpSession session) {
        List<ItemCarrinhoDTO> itens = obterItensDaSessao(session);

        // o percorre item por item da lista e puxa o getSubtotal() do ItemCarrinhoDTO
        BigDecimal total = itens.stream()
                .map(ItemCarrinhoDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return ResponseEntity.ok(total);
    }

    // recupera o carrinho da sessão ou cria um vazio caso ainda não exista
    private List<ItemCarrinhoDTO> obterItensDaSessao(HttpSession session) {
        List<ItemCarrinhoDTO> itens = (List<ItemCarrinhoDTO>) session.getAttribute("ITENS_CARRINHO");
        if (itens == null) {
            itens = new ArrayList<>();
            session.setAttribute("ITENS_CARRINHO", itens);
        }
        return itens;
    }


}
