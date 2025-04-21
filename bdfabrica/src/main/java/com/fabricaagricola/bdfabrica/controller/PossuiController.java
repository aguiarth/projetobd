package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.Possui;
import com.fabricaagricola.bdfabrica.model.PossuiId;
import com.fabricaagricola.bdfabrica.repository.PossuiRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/possui")
public class PossuiController {

    @Autowired
    private PossuiRepository repository;

    @PostMapping
    public Possui criar(@RequestBody Possui possui) {
        return repository.save(possui);
    }

    @GetMapping
    public List<Possui> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{cnpj}/{idConta}")
    public ResponseEntity<Possui> buscarPorId(@PathVariable String cnpj, @PathVariable int idConta) {
        PossuiId id = new PossuiId(cnpj, idConta);
        Optional<Possui> possui = repository.findById(id);
        return possui.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cnpj}/{idConta}")
    public ResponseEntity<Void> deletar(@PathVariable String cnpj, @PathVariable int idConta) {
        PossuiId id = new PossuiId(cnpj, idConta);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
