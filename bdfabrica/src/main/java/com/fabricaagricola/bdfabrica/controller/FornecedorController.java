package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.Fornecedor;
import com.fabricaagricola.bdfabrica.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping
    public List<Fornecedor> listarTodos() {
        return fornecedorRepository.findAll();
    }

    @GetMapping("/{cnpj}")
    public Optional<Fornecedor> buscarPorCnpj(@PathVariable String cnpj) {
        return fornecedorRepository.findById(cnpj);
    }

    @PostMapping
    public Fornecedor criar(@RequestBody Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    @PutMapping("/{cnpj}")
    public Fornecedor atualizar(@PathVariable String cnpj, @RequestBody Fornecedor fornecedorAtualizado) {
        fornecedorAtualizado.setCnpj(cnpj);
        return fornecedorRepository.save(fornecedorAtualizado);
    }

    @DeleteMapping("/{cnpj}")
    public void deletar(@PathVariable String cnpj) {
        fornecedorRepository.deleteById(cnpj);
    }
}
