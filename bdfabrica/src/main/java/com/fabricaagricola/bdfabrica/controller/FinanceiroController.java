package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.Financeiro;
import com.fabricaagricola.bdfabrica.service.FinanceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/financeiro")
public class FinanceiroController {

    private final FinanceiroService financeiroService;

    @Autowired
    public FinanceiroController(FinanceiroService financeiroService) {
        this.financeiroService = financeiroService;
    }

    // Criar
    @PostMapping
    public ResponseEntity<Financeiro> criar(@RequestBody Financeiro financeiro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(financeiroService.salvar(financeiro));
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Financeiro>> listarTodos() {
        return ResponseEntity.ok(financeiroService.listarTodos());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Financeiro> buscarPorId(@PathVariable int id) {
        return financeiroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Financeiro> atualizar(@PathVariable int id, @RequestBody Financeiro financeiro) {
        if (financeiro.getIdFinanceiro() != id) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(financeiroService.atualizar(financeiro));
    }

    // Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        financeiroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
