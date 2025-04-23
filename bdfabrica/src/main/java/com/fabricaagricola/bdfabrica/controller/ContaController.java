package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.Conta;
import com.fabricaagricola.bdfabrica.service.ContaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/conta")
public class ContaController {

	private final ContaService contaService;
	
	@Autowired
	public ContaController(ContaService contaService) {
		this.contaService = contaService;
	}
	
	@PostMapping
	public ResponseEntity<Conta> criar(@RequestBody Conta conta){
		return ResponseEntity.status(HttpStatus.CREATED).body(contaService.salvar(conta));
	}
	
	@GetMapping
	public ResponseEntity<List<Conta>> listarTodos() {
		return ResponseEntity.ok(contaService.listarTodos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Conta> buscarPorId(@PathVariable int id) {
		return contaService.buscarPorId(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
		
	@PutMapping("/{id}")
	public ResponseEntity<Conta> atualizar(@PathVariable int id, @RequestBody Conta conta) {
		if (conta.getIdConta() != id) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(contaService.atualizar(conta));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable int id) {
		contaService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
