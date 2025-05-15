package com.fabricaagricola.bdfabrica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabricaagricola.bdfabrica.model.Envia;
import com.fabricaagricola.bdfabrica.service.EnviaService;

@RestController
@RequestMapping("/api/envia")
public class EnviaController {
	private final EnviaService enviaService;

	@Autowired
	public EnviaController(EnviaService enviaService) {
		this.enviaService = enviaService;
	}

	@PostMapping
	public ResponseEntity<String> criarRelacionamento(@RequestBody Envia envia) {
		enviaService.salvarRelacionamento(envia);
		return ResponseEntity.ok("Relacionamento criado com sucesso.");
	}

	@GetMapping
	public ResponseEntity<List<Envia>> listarRelacionamentos() {
		return ResponseEntity.ok(enviaService.listarTodos());
	}

	@GetMapping("/{idExpedicao}")
	public ResponseEntity<List<Envia>> buscarPorExpedicao(@PathVariable int idExpedicao) {
		List<Envia> lista = enviaService.buscarPorId(idExpedicao);
		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{idExpedicao}/{numero}")
	public ResponseEntity<Envia> buscarRelacionamentoEspecifico(@PathVariable int idExpedicao, @PathVariable String numero) {
		Envia envia = enviaService.buscarRelacionamento(idExpedicao, numero);
		if (envia == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(envia);
	}
	
	@DeleteMapping("/{idExpedicao}/{numero}")
    public ResponseEntity<String> deletarRelacionamento(@PathVariable int idExpedicao, @PathVariable String numero) {
        enviaService.deletarRelacionamento(idExpedicao, numero);
        return ResponseEntity.ok("Relacionamento deletado com sucesso.");
    }
}
