package com.Anelie.vitrineVirtual.repositories;

import com.Anelie.vitrineVirtual.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}