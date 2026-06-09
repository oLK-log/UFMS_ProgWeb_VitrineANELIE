package com.Anelie.vitrineVirtual.repositories;

import com.Anelie.vitrineVirtual.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Busca uma categoria pelo nome
    Categoria findByNome(String nome);
}