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

import com.fabricaagricola.bdfabrica.model.Realiza;
import com.fabricaagricola.bdfabrica.service.RealizaService;

@RestController
@RequestMapping("/api/realiza")
public class RealizaController {
	private final RealizaService realizaService;

    @Autowired
    public RealizaController(RealizaService realizaService) {
        this.realizaService = realizaService;
    }
    
    @PostMapping
    public ResponseEntity<String> criarRelacionamento(@RequestBody Realiza realiza) {
        realizaService.salvarRelacionamento(realiza);
        return ResponseEntity.ok("Relacionamento criado com sucesso.");
    }
    
    @GetMapping
    public ResponseEntity<List<Realiza>> listarRelacionamentos() {
        return ResponseEntity.ok(realizaService.listarTodos());
    }
    
    @GetMapping("/{cnpj}")
    public ResponseEntity<List<Realiza>> buscarPorCliente(@PathVariable String cnpj) {
        List<Realiza> lista = realizaService.buscarPorCnpj(cnpj);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
    
    @GetMapping("/{cnpj}/{numero}")
    public ResponseEntity<Realiza> buscarRelacionamentoEspecifico(@PathVariable String cnpj, @PathVariable String numero) {
        Realiza realiza = realizaService.buscarRelacionamento(cnpj, numero);
        if (realiza == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(realiza);
    }
    
    @DeleteMapping("/{cnpj}/{numero}")
    public ResponseEntity<String> deletarRelacionamento(@PathVariable String cnpj, @PathVariable String numero) {
        realizaService.deletarRelacionamento(cnpj, numero);
        return ResponseEntity.ok("Relacionamento deletado com sucesso.");
    }
}
