package com.fabricaagricola.bdfabrica.repository;

import com.fabricaagricola.bdfabrica.model.NotaFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, String> {
}
