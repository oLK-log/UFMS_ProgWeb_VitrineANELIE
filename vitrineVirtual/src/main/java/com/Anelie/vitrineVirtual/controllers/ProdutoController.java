package com.Anelie.vitrineVirtual.controllers;

import com.Anelie.vitrineVirtual.models.Produto;
import com.Anelie.vitrineVirtual.repositories.ProdutoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // ── Área Cliente ─────────────────────────────────────────────────────────

    // Vitrine — listagem pública de produtos
    @GetMapping("/")
    public String exibirVitrine(Model model) {
        List<Produto> listaDeProdutos = produtoRepository.findAll();
        model.addAttribute("produtos", listaDeProdutos);
        return "vitrine";
    }

    // Detalhes de um produto
    @GetMapping("/produto/{id}")
    public String exibirDetalhesProduto(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
        model.addAttribute("produto", produto);
        return "detalhes_produto";
    }

    // Pedido finalizado — tela de conclusão com copia/cola
    // @GetMapping("/pedido-finalizado")
    // public String exibirPedidoFinalizado() {
    // return "pedido_finalizado";
    // }

    // ── Área Lojista ──────────────────────────────────────────────────────────

    // Exibe formulário de cadastro (objeto vazio)
    @GetMapping("/admin/cadastro")
    public String exibirCadastro(Model model) {
        model.addAttribute("produto", new Produto());
        return "admin/cadastro_produto";
    }

    // Salva novo produto
    @PostMapping("/admin/cadastro")
    public String salvarProduto(Produto produto, RedirectAttributes redirectAttributes) {
        produtoRepository.save(produto);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "O registro da joia foi guardado com sucesso!");
        return "redirect:/admin/inicio";
    }

    // Carrega produto para edição
    @GetMapping("/admin/editar/{id}")
    public String exibirEdicao(@PathVariable("id") Long id, Model model) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id do produto inválido: " + id));
        model.addAttribute("produto", produto);
        return "admin/cadastro_produto";
    }

    // Salva edição do produto
    @PostMapping("/admin/editar/{id}")
    public String salvarEdicao(@PathVariable("id") Long id, Produto produto, RedirectAttributes redirectAttributes) {
        produto.setId(id);
        produtoRepository.save(produto);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Joia atualizada com sucesso!");
        return "redirect:/admin/inicio";
    }

    // Exclui produto
    @GetMapping("/admin/excluir/{id}")
    public String excluirProduto(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        produtoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Produto deletado com sucesso!");
        return "redirect:/admin/inicio";
    }
}
