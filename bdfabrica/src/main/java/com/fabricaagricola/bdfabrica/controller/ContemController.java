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

import com.fabricaagricola.bdfabrica.model.Contem;
import com.fabricaagricola.bdfabrica.service.ContemService;

@RestController
@RequestMapping("/api/contem")
public class ContemController {
	private final ContemService contemService;

    @Autowired
    public ContemController(ContemService contemService) {
        this.contemService = contemService;
    }
    
    @PostMapping
    public ResponseEntity<String> criarRelacionamento(@RequestBody Contem contem) {
        contemService.salvarRelacionamento(contem);
        return ResponseEntity.ok("Relacionamento criado com sucesso.");
    }
    
    @GetMapping
    public ResponseEntity<List<Contem>> listarRelacionamentos() {
        return ResponseEntity.ok(contemService.listarTodos());
    }
    
    @GetMapping("/{numero}")
    public ResponseEntity<List<Contem>> buscarPorCliente(@PathVariable String numero) {
        List<Contem> lista = contemService.buscarPorNumero(numero);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
    
    @GetMapping("/{numero}/{idProduto}")
    public ResponseEntity<Contem> buscarRelacionamentoEspecifico(@PathVariable String numero, @PathVariable int idProduto) {
    	Contem contem = contemService.buscarRelacionamento(numero, idProduto);
        if (contem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contem);
    }
    
    @DeleteMapping("/{numero}/{idProduto}")
    public ResponseEntity<String> deletarRelacionamento(@PathVariable String numero, @PathVariable int idProduto) {
        contemService.deletarRelacionamento(numero, idProduto);
        return ResponseEntity.ok("Relacionamento deletado com sucesso.");
    }
}
