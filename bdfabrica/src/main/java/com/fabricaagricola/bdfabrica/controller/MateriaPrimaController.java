package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.MateriaPrima;
import com.fabricaagricola.bdfabrica.service.MateriaPrimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiaprima")
public class MateriaPrimaController {

    private final MateriaPrimaService service;

    @Autowired
    public MateriaPrimaController(MateriaPrimaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MateriaPrima> criar(@RequestBody MateriaPrima materiaPrima) {
        MateriaPrima criada = service.criarMateriaPrima(materiaPrima);
        return ResponseEntity.ok(criada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaPrima> buscarPorId(@PathVariable int id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MateriaPrima>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaPrima> atualizar(@PathVariable int id, @RequestBody MateriaPrima materiaPrima) {
        if (!service.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        materiaPrima.setIdMateriaPrima(id);
        return ResponseEntity.ok(service.atualizarMateriaPrima(materiaPrima));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        if (!service.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deletarMateriaPrima(id);
        return ResponseEntity.noContent().build();
    }
}
