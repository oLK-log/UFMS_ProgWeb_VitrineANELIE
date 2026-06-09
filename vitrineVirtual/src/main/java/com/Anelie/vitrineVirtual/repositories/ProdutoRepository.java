package com.Anelie.vitrineVirtual.repositories;

import com.Anelie.vitrineVirtual.models.Produto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Busca apenas onde destaque = true
    List<Produto> findByDestaqueTrue();

    // Busca apenas onde oferta = true
    List<Produto> findByOfertaTrue();

    // Busca ordenada pela data de cadastro decrescente-mais novos primeiro)
    // O Pageable permiti limitar a quantidade limite
    List<Produto> findByOrderByDataCadastroDesc(Pageable pageable);

    // Busca todos os produtos que pertencem a uma categoria específica
    List<Produto> findByCategoriaId(Long categoriaId);
}