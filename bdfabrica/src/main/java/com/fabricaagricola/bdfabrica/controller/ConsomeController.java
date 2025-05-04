package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.Consome;
import com.fabricaagricola.bdfabrica.service.ConsomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consome")
public class ConsomeController {

    private final ConsomeService consomeService;

    @Autowired
    public ConsomeController(ConsomeService consomeService) {
        this.consomeService = consomeService;
    }

    // Criar novo relacionamento
    @PostMapping
    public ResponseEntity<String> salvar(@RequestBody Consome consome) {
        try {
            consomeService.salvarRelacionamento(consome);
            return ResponseEntity.ok("Relacionamento salvo com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // Buscar todos os relacionamentos
    @GetMapping
    public ResponseEntity<List<Consome>> listarTodos() {
        try {
            return ResponseEntity.ok(consomeService.listarTodos());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Buscar todos os relacionamentos por idOrdem
    @GetMapping("/{idOrdem}")
    public ResponseEntity<List<Consome>> buscarPorIdOrdem(@PathVariable int idOrdem) {
        try {
            return ResponseEntity.ok(consomeService.buscarPorIdOrdem(idOrdem));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Buscar relacionamento específico por idOrdem e idMateriaPrima
    @GetMapping("/{idOrdem}/{idMateriaPrima}")
    public ResponseEntity<Consome> buscarPorIds(@PathVariable int idOrdem, @PathVariable int idMateriaPrima) {
        try {
            Consome consome = consomeService.buscarRelacionamento(idOrdem, idMateriaPrima);
            if (consome != null) {
                return ResponseEntity.ok(consome);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Deletar relacionamento por idOrdem e idMateriaPrima
    @DeleteMapping("/{idOrdem}/{idMateriaPrima}")
    public ResponseEntity<String> deletar(@PathVariable int idOrdem, @PathVariable int idMateriaPrima) {
        try {
            consomeService.deletarRelacionamento(idOrdem, idMateriaPrima);
            return ResponseEntity.ok("Relacionamento deletado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Erro ao deletar relacionamento.");
        }
    }
}
