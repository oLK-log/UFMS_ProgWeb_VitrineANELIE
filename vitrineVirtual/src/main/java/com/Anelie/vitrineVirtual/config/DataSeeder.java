package com.Anelie.vitrineVirtual.config;

import com.Anelie.vitrineVirtual.models.Produto;
import com.Anelie.vitrineVirtual.models.Usuario;
import com.Anelie.vitrineVirtual.repositories.ProdutoRepository;
import com.Anelie.vitrineVirtual.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository,
                                   ProdutoRepository produtoRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {

            if (usuarioRepository.findByLogin("lojista@anelie.com.br").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setLogin("lojista@anelie.com.br");
                admin.setSenha(passwordEncoder.encode("admin123"));
                usuarioRepository.save(admin);
                System.out.println("=========================================");
                System.out.println(" CHAVE MESTRA CRIADA COM SUCESSO!");
                System.out.println(" Login: lojista@anelie.com.br");
                System.out.println(" Senha: admin123");
                System.out.println("=========================================");
            }

            if (produtoRepository.count() == 0) {
                Produto anel = new Produto();
                anel.setNome("Anel Floral Prata");
                anel.setCategoria("Anel");
                anel.setMaterial("Prata 925 com zircônias");
                anel.setValor(new BigDecimal("189.90"));
                anel.setImagem("/Anel.jpg");
                anel.setDescricao("Anel delicado em prata 925 com design floral cravejado de zircônias brancas e verdes.");
                produtoRepository.save(anel);

                Produto brinco = new Produto();
                brinco.setNome("Brinco Lua e Sol Prata");
                brinco.setCategoria("Brinco");
                brinco.setMaterial("Prata 925 com zircônias");
                brinco.setValor(new BigDecimal("149.90"));
                brinco.setImagem("/Brinco.jpg");
                brinco.setDescricao("Par de brincos em prata 925 com detalhes de lua crescente e mini botão cravejado com zircônias.");
                produtoRepository.save(brinco);

                Produto colar = new Produto();
                colar.setNome("Colar Choker Cristais");
                colar.setCategoria("Colar");
                colar.setMaterial("Prata 925 com cristais");
                colar.setValor(new BigDecimal("229.90"));
                colar.setImagem("/colar.jpg");
                colar.setDescricao("Choker delicado em corrente veneziana de prata 925 com pingentes de cristais lapidados.");
                produtoRepository.save(colar);

                System.out.println("=========================================");
                System.out.println(" PRODUTOS DE EXEMPLO CADASTRADOS!");
                System.out.println(" Anel Floral Prata");
                System.out.println(" Brinco Lua e Sol Prata");
                System.out.println(" Colar Choker Cristais");
                System.out.println("=========================================");
            }
        };
    }
}