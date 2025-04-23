package com.fabricaagricola.bdfabrica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabricaagricola.bdfabrica.model.Cliente;
import com.fabricaagricola.bdfabrica.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	private ClienteService clienteService;
	
	@Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
	
	// Criar
    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.salvar(cliente));
    }
    
    // Listar todos
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }
    
    // Buscar por ID
    @GetMapping("/{cnpj}")
    public ResponseEntity<Cliente> buscarPorCnpj(@PathVariable String cnpj) {
        return clienteService.buscarPorCnpj(cnpj)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Atualizar
    @PutMapping("/{cnpj}")
    public ResponseEntity<Cliente> atualizar(@PathVariable String cnpj, @RequestBody Cliente cliente) {
        if (!cliente.getCnpjCliente().equals(cnpj)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(clienteService.atualizar(cliente));
    }
    
    // Deletar
    @DeleteMapping("/{cnpj}")
    public ResponseEntity<Void> deletar(@PathVariable String cnpj) {
    	clienteService.excluir(cnpj);
        return ResponseEntity.noContent().build();
    }
}
