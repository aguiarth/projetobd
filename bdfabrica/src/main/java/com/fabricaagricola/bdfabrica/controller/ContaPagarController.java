package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.ContaPagar;
import com.fabricaagricola.bdfabrica.repository.ContaPagarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contas-pagar")
public class ContaPagarController {

    @Autowired
    private ContaPagarRepository repository;

    @PostMapping
    public ContaPagar criar(@RequestBody ContaPagar conta) {
        return repository.save(conta);
    }

    @GetMapping
    public List<ContaPagar> listarTodas() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaPagar> buscarPorId(@PathVariable int id) {
        Optional<ContaPagar> conta = repository.findById(id);
        return conta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaPagar> atualizar(@PathVariable int id, @RequestBody ContaPagar novaConta) {
        return repository.findById(id).map(conta -> {
            conta.setDataEmissao(novaConta.getDataEmissao());
            conta.setDataVencimento(novaConta.getDataVencimento());
            conta.setValorTotal(novaConta.getValorTotal());
            conta.setStatus(novaConta.getStatus());
            conta.setFinanceiro(novaConta.getFinanceiro());
            conta.setFornecedor(novaConta.getFornecedor());
            return ResponseEntity.ok(repository.save(conta));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
