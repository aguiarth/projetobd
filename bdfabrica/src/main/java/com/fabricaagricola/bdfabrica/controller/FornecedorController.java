package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.Fornecedor;
import com.fabricaagricola.bdfabrica.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedor")
public class FornecedorController {

	private final FornecedorService fornecedorService;
	
	@Autowired
	public FornecedorController(FornecedorService fornecedorService) {
		this.fornecedorService = fornecedorService;
	}
	
	// Criar
	@PostMapping
	public ResponseEntity<Fornecedor> criar(@RequestBody Fornecedor fornecedor){ 
		return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorService.salvar(fornecedor));
	}
	
	 // Listar todos
    @GetMapping
    public ResponseEntity<List<Fornecedor>> listarTodos() {
        return ResponseEntity.ok(fornecedorService.listarTodos());
    }

    // Buscar por cnpj
    @GetMapping("/{cnpj}")
    public ResponseEntity<Fornecedor> buscarPorCnpj(@PathVariable String cnpj) {
        return fornecedorService.buscarPorCnpj(cnpj)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar
    @PutMapping("/{cnpj}")
    public ResponseEntity<Fornecedor> atualizar(@PathVariable String cnpj, @RequestBody Fornecedor fornecedor) {
    	if (!fornecedor.getCnpj().equals(cnpj)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(fornecedorService.atualizar(fornecedor));
    }

    // Deletar
    @DeleteMapping("/{cnpj}")
    public ResponseEntity<Void> deletar(@PathVariable String cnpj) {
        fornecedorService.excluir(cnpj);
        return ResponseEntity.noContent().build();
    }
	
}
