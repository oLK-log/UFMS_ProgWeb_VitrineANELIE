package com.Anelie.vitrineVirtual.repositories;

import com.Anelie.vitrineVirtual.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //metd que o spring usa para verificar se o email inserido existe
    Optional<Usuario> findByLogin(String login);
}
