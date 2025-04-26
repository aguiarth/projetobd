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

import com.fabricaagricola.bdfabrica.model.Expedicao;
import com.fabricaagricola.bdfabrica.service.ExpedicaoService;

@RestController
@RequestMapping("/api/expedicao")
public class ExpedicaoController {
	private ExpedicaoService expedicaoService;
	
	@Autowired
	public ExpedicaoController(ExpedicaoService expedicaoService) {
		this.expedicaoService = expedicaoService;
	}
	
	@PostMapping
	public ResponseEntity<Expedicao> criar(@RequestBody Expedicao expedicao){
		return ResponseEntity.status(HttpStatus.CREATED).body(expedicaoService.salvar(expedicao));
	}
	
	@GetMapping
	public ResponseEntity<List<Expedicao>> listarTodos() {
		return ResponseEntity.ok(expedicaoService.listarTodos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Expedicao> buscarPorId(@PathVariable int id) {
		return expedicaoService.buscarPorId(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
		
	@PutMapping("/{id}")
	public ResponseEntity<Expedicao> atualizar(@PathVariable int id, @RequestBody Expedicao expedicao) {
		if (expedicao.getIdExpedicao() != id) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(expedicaoService.atualizar(expedicao));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable int id) {
		expedicaoService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
