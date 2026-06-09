package com.Anelie.vitrineVirtual.config;

import com.Anelie.vitrineVirtual.models.Usuario;
import com.Anelie.vitrineVirtual.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    //aqui provisoriamente estao as credenciais padra. obs: pensar se vamos permitir gerar outras
    private final String defaultAdminLogin = "admin@anelie.com.br";
    private final String defaultAdminPassword = "admin123";

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            logger.info("Verificando se existe admin padrão.");

            // Verifica se o usuário já existe no banco para não criar duplicado toda vez que reiniciar
            if (usuarioRepository.findByLogin(defaultAdminLogin).isEmpty()) {

                Usuario admin = new Usuario();
                admin.setLogin(defaultAdminLogin);
                // A senha é criptografada antes de ir para o banco
                admin.setSenha(passwordEncoder.encode(defaultAdminPassword));

                usuarioRepository.save(admin);

                System.out.println("=========================================");
                System.out.println(" CHAVE MESTRA CRIADA COM SUCESSO!");
                System.out.println(" Login: " + defaultAdminLogin);
                System.out.println(" Senha: " + defaultAdminPassword);
                System.out.println("=========================================");
            } else {
                logger.info("Usuário admin já existente no banco de dados.");
            }
        };
    }
}