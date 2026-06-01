package com.Anelie.vitrineVirtual.repositories;

import com.Anelie.vitrineVirtual.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByUsername(String username);

}
