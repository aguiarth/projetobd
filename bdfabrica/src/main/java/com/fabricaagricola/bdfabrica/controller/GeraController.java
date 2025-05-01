package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.Gera;
import com.fabricaagricola.bdfabrica.service.GeraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gera")
public class GeraController {

    private final GeraService geraService;

    @Autowired
    public GeraController(GeraService geraService) {
        this.geraService = geraService;
    }

    @PostMapping
    public ResponseEntity<String> criarRelacionamento(@RequestBody Gera gera) {
        geraService.salvarRelacionamento(gera);
        return ResponseEntity.ok("Relacionamento criado com sucesso.");
    }

    @GetMapping
    public ResponseEntity<List<Gera>> listarRelacionamentos() {
        return ResponseEntity.ok(geraService.listarTodos());
    }

    @GetMapping("/{idOrdem}")
    public ResponseEntity<List<Gera>> buscarPorOrdem(@PathVariable int idOrdem) {
        List<Gera> lista = geraService.buscarPorOrdem(idOrdem);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
    
    @GetMapping("/{idOrdem}/{idProduto}")
    public ResponseEntity<Gera> buscarRelacionamentoEspecifico(@PathVariable int idOrdem, @PathVariable int idProduto) {
        Gera gera = geraService.buscarRelacionamento(idOrdem, idProduto);
        if (gera == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gera);
    }

    @DeleteMapping("/{idOrdem}/{idProduto}")
    public ResponseEntity<String> deletarRelacionamento(@PathVariable int idOrdem, @PathVariable int idProduto) {
        geraService.deletarRelacionamento(idOrdem, idProduto);
        return ResponseEntity.ok("Relacionamento deletado com sucesso.");
    }
}
