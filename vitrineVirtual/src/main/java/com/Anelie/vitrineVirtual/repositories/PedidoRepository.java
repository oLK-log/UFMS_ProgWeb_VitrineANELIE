package com.Anelie.vitrineVirtual.repositories;

import com.Anelie.vitrineVirtual.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}