package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.Vincula;
import com.fabricaagricola.bdfabrica.service.VinculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vincula")
public class VinculaController {

    private final VinculaService vinculaService;

    @Autowired
    public VinculaController(VinculaService vinculaService) {
        this.vinculaService = vinculaService;
    }

    @PostMapping
    public ResponseEntity<String> criarRelacionamento(@RequestBody Vincula vincula) {
        vinculaService.salvarRelacionamento(vincula);
        return ResponseEntity.ok("Relacionamento criado com sucesso.");
    }

    @GetMapping
    public ResponseEntity<List<Vincula>> listarRelacionamentos() {
        return ResponseEntity.ok(vinculaService.listarTodos());
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<List<Vincula>> buscarPorFornecedor(@PathVariable String cnpj) {
        List<Vincula> lista = vinculaService.buscarPorCnpj(cnpj);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{cnpj}/{codigo}")
    public ResponseEntity<Vincula> buscarRelacionamentoEspecifico(@PathVariable String cnpj, @PathVariable String codigo) {
        Vincula vincula = vinculaService.buscarRelacionamento(cnpj, codigo);
        if (vincula == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vincula);
    }

    @DeleteMapping("/{cnpj}/{codigo}")
    public ResponseEntity<String> deletarRelacionamento(@PathVariable String cnpj, @PathVariable String codigo) {
        vinculaService.deletarRelacionamento(cnpj, codigo);
        return ResponseEntity.ok("Relacionamento deletado com sucesso.");
    }
}
