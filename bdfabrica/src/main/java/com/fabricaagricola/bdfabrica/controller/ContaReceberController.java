package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.ContaReceber;
import com.fabricaagricola.bdfabrica.repository.ContaReceberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/conta-receber") // <-- Esse é o endpoint que precisa existir
public class ContaReceberController {

    @Autowired
    private ContaReceberRepository repository;

    @PostMapping
    public ContaReceber criar(@RequestBody ContaReceber conta) {
        return repository.save(conta);
    }

    @GetMapping
    public List<ContaReceber> listarTodas() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaReceber> buscarPorId(@PathVariable int id) {
        Optional<ContaReceber> conta = repository.findById(id);
        return conta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaReceber> atualizar(@PathVariable int id, @RequestBody ContaReceber novaConta) {
        return repository.findById(id).map(conta -> {
            conta.setDataEmissao(novaConta.getDataEmissao());
            conta.setDataVencimento(novaConta.getDataVencimento());
            conta.setValorTotal(novaConta.getValorTotal());
            conta.setStatus(novaConta.getStatus());
            conta.setFinanceiro(novaConta.getFinanceiro());
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
