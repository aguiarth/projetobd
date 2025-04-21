package com.fabricaagricola.bdfabrica.repository;

import com.fabricaagricola.bdfabrica.model.Possui;
import com.fabricaagricola.bdfabrica.model.PossuiId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PossuiRepository extends JpaRepository<Possui, PossuiId> {

    // Lista todas as contas que um cliente possui
    List<Possui> findByIdCnpj(String cnpj);

    // Lista todos os clientes que possuem uma conta específica
    List<Possui> findByIdIdConta(int idConta);
}
