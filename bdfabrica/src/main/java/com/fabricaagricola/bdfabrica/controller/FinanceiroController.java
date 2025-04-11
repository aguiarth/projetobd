package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.Financeiro;
import com.fabricaagricola.bdfabrica.repository.FinanceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/financeiros")
public class FinanceiroController {

    @Autowired
    private FinanceiroRepository financeiroRepository;

    // Criar
    @PostMapping
    public Financeiro createFinanceiro(@RequestBody Financeiro financeiro) {
        return financeiroRepository.save(financeiro);
    }

    // Listar todos
    @GetMapping
    public List<Financeiro> getAllFinanceiros() {
        return financeiroRepository.findAll();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Financeiro> getFinanceiroById(@PathVariable int id) {
        Optional<Financeiro> financeiro = financeiroRepository.findById(id);
        return financeiro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Financeiro> updateFinanceiro(@PathVariable int id, @RequestBody Financeiro atualizado) {
        return financeiroRepository.findById(id).map(financeiro -> {
            financeiro.setHistoricoLucro(atualizado.getHistoricoLucro());
            financeiro.setHistoricoPrejuizo(atualizado.getHistoricoPrejuizo());
            financeiro.setDataAtualizacao(atualizado.getDataAtualizacao());
            return ResponseEntity.ok(financeiroRepository.save(financeiro));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinanceiro(@PathVariable int id) {
        if (financeiroRepository.existsById(id)) {
            financeiroRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
