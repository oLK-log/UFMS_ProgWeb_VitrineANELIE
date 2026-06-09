package com.Anelie.vitrineVirtual.controllers;

import com.Anelie.vitrineVirtual.models.Produto;
import com.Anelie.vitrineVirtual.repositories.ProdutoRepository;
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

@Controller
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // --- ÁREA CLIENTE ---
    @GetMapping("/")
    public String exibirVitrine(Model model) {
        List<Produto> listaDeProdutos = produtoRepository.findAll();
        model.addAttribute("produtos", listaDeProdutos);
        return "vitrine";
    }

    // --- ÁREA LOJISTA ---
    @GetMapping("/admin/cadastro")
    public String exibirCadastro(Model model) {
        model.addAttribute("produto", new Produto());
        return "admin/cadastro_produto";
    }

    @PostMapping("/admin/produtos/salvar")
    public String salvarProduto(@ModelAttribute Produto produto,
                                @RequestParam("file") MultipartFile file,
                                RedirectAttributes redirectAttributes) {
        try {
            // prevencao da perda de imagem caso editar e enviar sem ft
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

            // feedback visual
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
        return "admin/cadastro_produto";
    }

    @GetMapping("/admin/excluir/{id}")
    public String excluirProduto(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        produtoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Produto deletado com sucesso!");
        return "redirect:/admin/inicio";
    }
}