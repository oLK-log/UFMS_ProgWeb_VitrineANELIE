package com.Anelie.vitrineVirtual.controllers;

import com.Anelie.vitrineVirtual.models.Categoria;
import com.Anelie.vitrineVirtual.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("novaCategoria", new Categoria());
        return "admin/categorias_admin";
    }

    @PostMapping("/salvar")
    public String salvarCategoria(@ModelAttribute("novaCategoria") Categoria categoria, RedirectAttributes redirectAttributes) {
        try {
            categoriaRepository.save(categoria);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Categoria cadastrada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao salvar categoria. Verifique se o nome já existe.");
        }
        return "redirect:/admin/categorias";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCategoria(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            categoriaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Categoria excluída com sucesso!");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            // Defesa do banco de dados: impede apagar categoria que tenha produtos nela
            redirectAttributes.addFlashAttribute("mensagemErro", "Não é possível excluir esta categoria pois existem joias vinculadas a ela no catálogo.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Ocorreu um erro interno ao tentar excluir a categoria.");
        }
        return "redirect:/admin/categorias";
    }
}