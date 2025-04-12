package com.fabricaagricola.bdfabrica.repository;

import com.fabricaagricola.bdfabrica.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, String> {
}
