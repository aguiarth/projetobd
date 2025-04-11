package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.Fornecedor;
import com.fabricaagricola.bdfabrica.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    // Listar todos os fornecedores
    @GetMapping
    public List<Fornecedor> listarTodos() {
        return fornecedorRepository.findAll();
    }

    // Buscar fornecedor por CNPJ
    @GetMapping("/{cnpj}")
    public ResponseEntity<Fornecedor> buscarPorCnpj(@PathVariable String cnpj) {
        return fornecedorRepository.findById(cnpj)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar novo fornecedor
    @PostMapping
    public Fornecedor criar(@RequestBody Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    // Atualizar fornecedor existente
    @PutMapping("/{cnpj}")
    public ResponseEntity<Fornecedor> atualizar(@PathVariable String cnpj, @RequestBody Fornecedor fornecedorAtualizado) {
        return fornecedorRepository.findById(cnpj).map(fornecedor -> {
            fornecedor.setRazaoSocial(fornecedorAtualizado.getRazaoSocial());
            fornecedor.setEndereco(fornecedorAtualizado.getEndereco());
            fornecedor.setTelefone(fornecedorAtualizado.getTelefone());
            fornecedor.setCondicoesPagamento(fornecedorAtualizado.getCondicoesPagamento());
            return ResponseEntity.ok(fornecedorRepository.save(fornecedor));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cnpj}")
    public ResponseEntity<Void> deletar(@PathVariable String cnpj) {
        return fornecedorRepository.findById(cnpj)
                .map(fornecedor -> {
                    fornecedorRepository.delete(fornecedor);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
