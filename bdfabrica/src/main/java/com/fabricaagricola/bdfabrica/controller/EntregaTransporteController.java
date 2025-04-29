package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.EntregaTransporte;
import com.fabricaagricola.bdfabrica.service.EntregaTransporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entregas")
public class EntregaTransporteController {

    private final EntregaTransporteService entregaService;

    @Autowired
    public EntregaTransporteController(EntregaTransporteService entregaService) {
        this.entregaService = entregaService;
    }

    @PostMapping
    public ResponseEntity<EntregaTransporte> criarEntrega(@RequestBody EntregaTransporte entrega) {
        EntregaTransporte salva = entregaService.salvar(entrega);
        return ResponseEntity.ok(salva);
    }

    @GetMapping("/{numeroEntrega}")
    public ResponseEntity<EntregaTransporte> buscarPorNumero(@PathVariable String numeroEntrega) {
        return entregaService.buscarPorNumero(numeroEntrega)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EntregaTransporte>> listarTodas() {
        return ResponseEntity.ok(entregaService.listarTodas());
    }

    @PutMapping("/{numeroEntrega}")
    public ResponseEntity<EntregaTransporte> atualizarEntrega(
            @PathVariable String numeroEntrega,
            @RequestBody EntregaTransporte entrega) {
        
        EntregaTransporte atualizada = entregaService.atualizar(numeroEntrega, entrega);
        return ResponseEntity.ok(atualizada);
    }


    @DeleteMapping("/{numeroEntrega}")
    public ResponseEntity<Void> deletarEntrega(@PathVariable String numeroEntrega) {
        entregaService.deletar(numeroEntrega);
        return ResponseEntity.noContent().build();
    }
}
