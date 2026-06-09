package com.Anelie.vitrineVirtual.controllers;

import com.Anelie.vitrineVirtual.models.Produto;
import com.Anelie.vitrineVirtual.repositories.CategoriaRepository;
import com.Anelie.vitrineVirtual.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;

@Controller
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // --- ÁREA CLIENTE ---
    @GetMapping("/")
    public String exibirVitrine(Model model) {
        // Definição do valor limite de produtos mais recentes (ex. 4 últimos)
        int limiteProdutosRecentes = 4;

        // Carrega as listas filtradas do banco de dados
        List<Produto> produtosDestaque = produtoRepository.findByDestaqueTrue();
        List<Produto> produtosOferta = produtoRepository.findByOfertaTrue();
        List<Produto> produtosRecentes = produtoRepository.findByOrderByDataCadastroDesc(
                PageRequest.of(0, limiteProdutosRecentes)
        );

        // Buscar catálogo
        List<Produto> todosProdutos = produtoRepository.findAll();

        // Envia cada lista separada para o Thymeleaf
        model.addAttribute("produtosDestaque", produtosDestaque);
        model.addAttribute("produtosOferta", produtosOferta);
        model.addAttribute("produtosRecentes", produtosRecentes);
        model.addAttribute("todosProdutos", todosProdutos);

        // Envia as categorias para o menu lateral da vitrine
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "vitrine";
    }

    // --- ÁREA LOJISTA ---
    @GetMapping("/admin/cadastro")
    public String exibirCadastro(Model model) {
        model.addAttribute("produto", new Produto());

        // CORREÇÃO: Envia a lista de categorias para popular o <select> do formulário
        model.addAttribute("categorias", categoriaRepository.findAll());

        return "admin/cadastro_produto";
    }

    @PostMapping("/admin/produtos/salvar")
    public String salvarProduto(@ModelAttribute Produto produto,
                                @RequestParam("file") MultipartFile file,
                                RedirectAttributes redirectAttributes) {
        try {
            // Prevenção da perda de imagem caso editar e enviar sem foto
            if (produto.getId() != null && file.isEmpty()) {
                Produto produtoExistente = produtoRepository.findById(produto.getId()).orElse(null);
                if (produtoExistente != null) {
                    produto.setImagem(produtoExistente.getImagem());
                }
            }

            // Se o usuário enviou uma foto nova (seja no cadastro inicial ou editando)
            if (!file.isEmpty()) {
                String nomeUnico = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path diretorioUpload = Paths.get("uploads");

                if (!Files.exists(diretorioUpload)) {
                    Files.createDirectories(diretorioUpload);
                }

                Path caminhoArquivo = diretorioUpload.resolve(nomeUnico);
                Files.copy(file.getInputStream(), caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);

                produto.setImagem("/uploads/" + nomeUnico);
            }

            produtoRepository.save(produto);

            // Feedback visual
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Produto salvo com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/admin/cadastro?erro";
        }
        return "redirect:/admin/inicio";
    }

    @GetMapping("/admin/editar/{id}")
    public String exibirEdicao(@PathVariable("id") Long id, Model model) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id do produto inválido: " + id));
        model.addAttribute("produto", produto);
        // Envia a lista de categorias para popular o <select> do formulário na edição
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "admin/cadastro_produto";
    }

    @GetMapping("/admin/excluir/{id}")
    public String excluirProduto(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            produtoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Joia deletada do catálogo com sucesso!");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            // O banco de dados bloqueia a exclusão porque a joia já faz parte de um pedido
            redirectAttributes.addFlashAttribute("mensagemErro", "Não é possível excluir esta joia pois ela já está vinculada ao histórico de vendas de um cliente.");
        } catch (Exception e) {
            // Outros erros inesperados
            redirectAttributes.addFlashAttribute("mensagemErro", "Ocorreu um erro interno ao tentar excluir a joia.");
        }
        return "redirect:/admin/inicio";
    }

    @GetMapping("/categoria/{id}")
    public String exibirPorCategoria(@PathVariable("id") Long id, Model model) {
        // 1. Identifica qual categoria o usuário clicou
        com.Anelie.vitrineVirtual.models.Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        // 2. Busca as joias apenas dessa categoria
        List<Produto> produtosCategoria = produtoRepository.findByCategoriaId(id);

        // 3. Envia os dados para a tela
        model.addAttribute("categoriaAtual", categoria);
        model.addAttribute("produtos", produtosCategoria);
        model.addAttribute("categorias", categoriaRepository.findAll()); // Para o menu lateral

        return "categoria"; // O nome do novo arquivo HTML
    }
}