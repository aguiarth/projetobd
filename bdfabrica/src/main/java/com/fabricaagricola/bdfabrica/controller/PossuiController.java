package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.Possui;
import com.fabricaagricola.bdfabrica.service.PossuiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/possui")
public class PossuiController {

    private final PossuiService possuiService;

    @Autowired
    public PossuiController(PossuiService possuiService) {
        this.possuiService = possuiService;
    }

    @PostMapping
    public ResponseEntity<String> salvar(@RequestBody Possui possui) {
    	possuiService.salvarRelacionamento(possui);
        return ResponseEntity.ok("Relacionamento salvo com sucesso.");
    }

    @GetMapping
    public ResponseEntity<List<Possui>> listarTodos() {
    	return ResponseEntity.ok(possuiService.listarTodos());
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<?> buscarPorCnpj(@PathVariable String cnpj) {
    	List<Possui> lista = possuiService.buscarPorCnpj(cnpj);
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{cnpj}/{idConta}")
    public ResponseEntity<?> buscarRelacionamento(@PathVariable String cnpj, @PathVariable int idConta) {
    	Possui possui = possuiService.buscarRelacionamento(cnpj, idConta);
        if (possui == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(possui);
    }

    @DeleteMapping("/{cnpj}/{idConta}")
    public ResponseEntity<String> deletar(@PathVariable String cnpj, @PathVariable int idConta) {
    	possuiService.deletarRelacionamento(cnpj, idConta);
        return ResponseEntity.ok("Relacionamento deletado com sucesso.");
    }
}
