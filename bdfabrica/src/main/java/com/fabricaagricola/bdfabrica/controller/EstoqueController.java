package com.fabricaagricola.bdfabrica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fabricaagricola.bdfabrica.model.Estoque;
import com.fabricaagricola.bdfabrica.service.EstoqueService;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    @Autowired
    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping
    public Estoque salvar(@RequestBody Estoque estoque) {
        return estoqueService.salvar(estoque);
    }

    @GetMapping("/{id}")
    public Estoque buscarPorId(@PathVariable int id) {
        return estoqueService.buscarPorId(id);
    }

    @GetMapping
    public List<Estoque> listarTodos() {
        return estoqueService.listarTodos();
    }

    @PutMapping("/{id}")
    public Estoque atualizar(@PathVariable int id, @RequestBody Estoque estoque) {
        estoque.setIdEstoque(id);
        return estoqueService.atualizar(estoque);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable int id) {
        estoqueService.deletar(id);
    }
}
