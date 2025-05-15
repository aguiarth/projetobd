package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.OrdemProducao;
import com.fabricaagricola.bdfabrica.service.OrdemProducaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordem-producao")
public class OrdemProducaoController {

	private final OrdemProducaoService service;

	@Autowired
	public OrdemProducaoController(OrdemProducaoService service) {
		this.service = service;
	}

	@PostMapping
	public OrdemProducao salvar(@RequestBody OrdemProducao ordem) {
		return service.salvar(ordem);
	}

	@GetMapping
	public List<OrdemProducao> listarTodas() {
		return service.listarTodas();
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrdemProducao> buscarPorId(@PathVariable int id) {
		Optional<OrdemProducao> ordem = service.buscarPorId(id);
		return ordem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}/ordem-dependente")
	public ResponseEntity<OrdemProducao> buscarOrdemDependente(@PathVariable int id) {
		Optional<OrdemProducao> ordem = service.buscarPorId(id);
		if (ordem.isPresent() && ordem.get().getIdOrdemDependente() != null) {
			Optional<OrdemProducao> ordemDependente = service.obterOrdemDependenteCompleta(ordem.get());
			return ordemDependente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}/ordem-requisitada")
	public ResponseEntity<OrdemProducao> buscarOrdemRequisitada(@PathVariable int id) {
		Optional<OrdemProducao> ordem = service.buscarPorId(id);
		if (ordem.isPresent() && ordem.get().getIdOrdemRequisitada() != null) {
			Optional<OrdemProducao> ordemRequisitada = service.obterOrdemRequisitadaCompleta(ordem.get());
			return ordemRequisitada.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<OrdemProducao> atualizar(@PathVariable int id, @RequestBody OrdemProducao ordem) {
		Optional<OrdemProducao> ordemExistente = service.buscarPorId(id);
		if (ordemExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		ordem.setIdOrdem(id);
		OrdemProducao ordemAtualizada = service.atualizar(ordem);
		return ResponseEntity.ok(ordemAtualizada);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable int id) {
		Optional<OrdemProducao> ordemExistente = service.buscarPorId(id);
		if (ordemExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
}