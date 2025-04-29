package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.OrdemProducao;
import com.fabricaagricola.bdfabrica.service.OrdemProducaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordens-producao")
public class OrdemProducaoController {

    private final OrdemProducaoService ordemProducaoService;

    @Autowired
    public OrdemProducaoController(OrdemProducaoService ordemProducaoService) {
        this.ordemProducaoService = ordemProducaoService;
    }

    @PostMapping
    public ResponseEntity<OrdemProducao> criar(@RequestBody OrdemProducao ordemProducao) {
        OrdemProducao criada = ordemProducaoService.salvarComRelacionamento(ordemProducao);
        return ResponseEntity.ok(criada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemProducao> buscarPorId(@PathVariable int id) {
        return ordemProducaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<OrdemProducao>> listarTodas() {
        return ResponseEntity.ok(ordemProducaoService.listarTodas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemProducao> atualizar(@PathVariable int id, @RequestBody OrdemProducao ordemProducao) {
        return ordemProducaoService.buscarPorId(id)
                .map(op -> {
                    ordemProducao.setIdOrdem(id);
                    OrdemProducao atualizada = ordemProducaoService.atualizar(ordemProducao);
                    return ResponseEntity.ok(atualizada);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        ordemProducaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
