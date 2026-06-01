package com.Anelie.vitrineVirtual.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.Anelie.vitrineVirtual.models.Produto;
import com.Anelie.vitrineVirtual.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProdutoController {

    private final ProdutoRepository produtoRepository; //instancia o repositório, com isso podemos usar os métodos do db

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    //area cliente
    //READ
    //Implementando Listagem de produtos na vitrine
    @GetMapping("/")//o Get envia/exibe os dados/tela
    public String exibirVitrine(Model model){
        List<Produto> listaDeProdutos = produtoRepository.findAll();//busca os prod
        model.addAttribute("produtos", listaDeProdutos);
        return "vitrine";
    }

    //area lojista
    //P Exibir tela de formulario de cadsatro
    @GetMapping("/admin/cadastro")
    public String exibirCadastro(Model model){
        //aqui estamos enviando um objeto vazio para o HTML
        //vamos precisar conectar os campo html com atributos do Prduto
        model.addAttribute("produto", new Produto());
        return "admin/cadastro_produto";
    }

    //Create
    //Metodo p salvar(criar) o produto no banco de dados
    @PostMapping("/admin/cadastro")
    public String salvarProduto(Produto produto, RedirectAttributes redirectAttributes){
        produtoRepository.save(produto);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "O registo da joia foi guardado com sucesso!");
        return "redirect:/admin/inicio";
    }

    //Update
    //Met para carregar os produtos na tela de cadastro
    @GetMapping("/admin/editar/{id}")
    public String exibirEdicao(@PathVariable("id")Long id, Model model){
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id do produto inválido:"+id));
        model.addAttribute("produto", produto);
        return "admin/cadastro_produto";
    }

    //Delete
    //exclui o prod
    @GetMapping("/admin/excluir/{id}")
    public String excluirProduto(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        produtoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Produto Deletado com Sucesso!");
        return "redirect:/admin/inicio";
    }
}
