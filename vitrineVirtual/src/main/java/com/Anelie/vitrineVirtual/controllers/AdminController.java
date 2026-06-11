package com.Anelie.vitrineVirtual.controllers;

import com.Anelie.vitrineVirtual.repositories.ProdutoRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin") //para tudo que se tratar do Lojista vamos usar a rota admin
public class AdminController {

    private final ProdutoRepository produtoRepository;
    public AdminController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/inicio")
    public String dashboardAdmin(Model model) {
        //busca todos os produtos
        model.addAttribute("produtos", produtoRepository.findAll());
        return "admin/inicio_admin";
    }
    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setMaxAge(0); // Expira o cookie imediatamente
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/login";
    }
}
