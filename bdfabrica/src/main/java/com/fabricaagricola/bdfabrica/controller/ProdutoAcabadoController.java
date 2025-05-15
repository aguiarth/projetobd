package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.ProdutoAcabado;
import com.fabricaagricola.bdfabrica.service.ProdutoAcabadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoAcabadoController {

    private final ProdutoAcabadoService produtoService;

    @Autowired
    public ProdutoAcabadoController(ProdutoAcabadoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoAcabado> criar(@RequestBody ProdutoAcabado produto) {
        ProdutoAcabado salvo = produtoService.salvar(produto);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoAcabado> buscarPorId(@PathVariable int id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProdutoAcabado>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoAcabado> atualizar(@PathVariable int id, @RequestBody ProdutoAcabado produto) {
        produto.setIdProduto(id);
        return ResponseEntity.ok(produtoService.atualizar(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
