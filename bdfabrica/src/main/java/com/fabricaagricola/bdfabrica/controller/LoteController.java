package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.Lote;
import com.fabricaagricola.bdfabrica.service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lote")
public class LoteController {

    private final LoteService loteService;

    @Autowired
    public LoteController(LoteService loteService) {
        this.loteService = loteService;
    }

    @PostMapping
    public ResponseEntity<Lote> criar(@RequestBody Lote lote) {
        Lote salvo = loteService.salvar(lote);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lote> buscarPorCodigo(@PathVariable String codigo) {
        return loteService.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Lote>> listarTodos() {
        return ResponseEntity.ok(loteService.listarTodos());
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Lote> atualizar(@PathVariable String codigo, @RequestBody Lote lote) {
        lote.setCodigo(codigo);
        return ResponseEntity.ok(loteService.atualizar(lote));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletar(@PathVariable String codigo) {
        loteService.deletar(codigo);
        return ResponseEntity.noContent().build();
    }
}
