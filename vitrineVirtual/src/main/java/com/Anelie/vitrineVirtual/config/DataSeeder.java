package com.Anelie.vitrineVirtual.config;

import com.Anelie.vitrineVirtual.models.Usuario;
import com.Anelie.vitrineVirtual.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica se o usuário já existe no banco para não criar duplicado toda vez que reiniciar
            if (usuarioRepository.findByLogin("lojista@anelie.com.br").isEmpty()) {

                Usuario admin = new Usuario();
                admin.setLogin("lojista@anelie.com.br");
                admin.setSenha(passwordEncoder.encode("admin123")); //senha criptografada

                usuarioRepository.save(admin);

                System.out.println("=========================================");
                System.out.println(" CHAVE MESTRA CRIADA COM SUCESSO!");
                System.out.println(" Login: lojista@anelie.com.br");
                System.out.println(" Senha: admin123");
                System.out.println("=========================================");
            }
        };
    }
}
