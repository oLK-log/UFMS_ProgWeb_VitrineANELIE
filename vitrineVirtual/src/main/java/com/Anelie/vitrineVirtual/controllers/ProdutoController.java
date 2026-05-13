package com.Anelie.vitrineVirtual.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.Anelie.vitrineVirtual.models.Produto;
import com.Anelie.vitrineVirtual.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;//instancia o repositório, com isso podemos usar os métodos do db

    //Implementando Listagem de produtos na vitrine
    @GetMapping("/")//o Get envia/exibe os dados/tela
    public String exibirVitrine(Model model){
        List<Produto> listaDeProdutos = produtoRepository.findAll();//busca os prod
        model.addAttribute("produtos", listaDeProdutos);
        return "vitrine";
    }

    //Exibir tela de formulario de cadsatro
    @GetMapping("/admin/cadastro")
    public String exibirCadastro(Model model){
        //aqui estamos enviando um objeto vazio para o HTML
        //vamos precisar conectar os campo html com atributos do Prduto
        model.addAttribute("produto", new Produto());
        return "cadastro_produto";
    }

    //Metodo final p salvar(criar) o produto no banco de dados
    @PostMapping("/admin/cadastro") // O Post recebe dados
    public String salvarProduto(Produto produto){
        produtoRepository.save(produto);
        return "redirect:/"; // redireciona para a vitrine-> obs: vamos usar bastante
    }
}
