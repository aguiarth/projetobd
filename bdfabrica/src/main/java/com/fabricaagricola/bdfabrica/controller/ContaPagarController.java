package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.ContaPagar;
import com.fabricaagricola.bdfabrica.service.ContaPagarService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/contaPagar")
public class ContaPagarController {

	private final ContaPagarService contaPagarService;
	
	@Autowired
	public ContaPagarController(ContaPagarService contaPagarService) {
		this.contaPagarService = contaPagarService;
	}
	
	@PostMapping
	public ResponseEntity<ContaPagar> criar(@RequestBody ContaPagar contaPagar){
		return ResponseEntity.status(HttpStatus.CREATED).body(contaPagarService.salvar(contaPagar));
	}
	
	@GetMapping
	public ResponseEntity<List<ContaPagar>> listarTodos(){
		return ResponseEntity.ok(contaPagarService.listarTodos());
	}
	
	@GetMapping("/{idConta}/{cnpj}")
	public ResponseEntity<ContaPagar> buscarPorPk(@PathVariable int idConta, @PathVariable String cnpj) {
		return contaPagarService.buscarPorPk(idConta, cnpj)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	
	@DeleteMapping("/{idConta}/{cnpj}")
	public ResponseEntity<Void> deletar(@PathVariable int idConta, @PathVariable String cnpj) {
		contaPagarService.excluir(idConta, cnpj);
		return ResponseEntity.noContent().build();
	}
	
}
