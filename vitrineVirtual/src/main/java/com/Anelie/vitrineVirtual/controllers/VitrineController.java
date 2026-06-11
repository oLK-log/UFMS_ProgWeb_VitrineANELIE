package com.Anelie.vitrineVirtual.controllers;

import com.Anelie.vitrineVirtual.dto.ProdutoDetalhadoDTO;
import com.Anelie.vitrineVirtual.models.Produto;
import com.Anelie.vitrineVirtual.repositories.ProdutoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VitrineController {

    private final ProdutoRepository produtoRepository;

    public VitrineController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // área cliente
    // READ
    // Implementando Listagem e Busca de produtos na vitrine
    @GetMapping("/")
    public String exibirVitrine(@RequestParam(name = "busca", required = false) String busca, Model model){
        List<Produto> listaDeProdutos;

        // Se o usuário digitou algo na busca, filtramos. Se não, mostramos tudo.
        if (busca != null && !busca.trim().isEmpty()) {
            listaDeProdutos = produtoRepository.findByNomeContainingIgnoreCase(busca);
            model.addAttribute("termoBusca", busca);
        } else {
            listaDeProdutos = produtoRepository.findAll();
        }

        model.addAttribute("produtos", listaDeProdutos);
        return "vitrine";
    }

    // Rota exigida para os detalhes da joia
    @GetMapping("/produto/{id}")
    public String exibirDetalhesJoia(@PathVariable("id") Long id, Model model) {
        // Busca o produto
        Produto produto = produtoRepository.findById(id).orElse(null);

        // Se nao achar volta para a vitrine
        if (produto == null) {
            return "redirect:/";
        }

        ProdutoDetalhadoDTO dto = new ProdutoDetalhadoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getDescricao(),
                produto.getMaterial(),
                produto.getImagem(),
                produto.getValor()
        );

        model.addAttribute("produtoDetalhe", dto);
        return "cliente/detalhes_produto";
    }
}