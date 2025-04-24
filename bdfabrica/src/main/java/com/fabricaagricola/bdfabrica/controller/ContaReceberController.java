package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.ContaReceber;
import com.fabricaagricola.bdfabrica.service.ContaReceberService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/contaReceber")
public class ContaReceberController {

	private final ContaReceberService contaReceberService;
	
	@Autowired
	public ContaReceberController(ContaReceberService contaReceberService) {
		this.contaReceberService = contaReceberService;
	}
	
	@PostMapping
	public ResponseEntity<ContaReceber> criar(@RequestBody ContaReceber contaReceber){
		return ResponseEntity.status(HttpStatus.CREATED).body(contaReceberService.salvar(contaReceber));
	}
	
	@GetMapping
	public ResponseEntity<List<ContaReceber>> listarTodos() {
		return ResponseEntity.ok(contaReceberService.listarTodos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ContaReceber> buscarPorId(@PathVariable int id) {
		return contaReceberService.buscarPorId(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable int id) {
		contaReceberService.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
}
