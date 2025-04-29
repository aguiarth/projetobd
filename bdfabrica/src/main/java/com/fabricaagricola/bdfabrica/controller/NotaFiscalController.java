package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.NotaFiscal;
import com.fabricaagricola.bdfabrica.service.NotaFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notas")
public class NotaFiscalController {

    private final NotaFiscalService notaFiscalService;

    @Autowired
    public NotaFiscalController(NotaFiscalService notaFiscalService) {
        this.notaFiscalService = notaFiscalService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotaFiscal criarNotaFiscal(@RequestBody NotaFiscal notaFiscal) {
        return notaFiscalService.salvarNotaFiscal(notaFiscal);
    }

    @GetMapping("/{id}")
    public Optional<NotaFiscal> buscarNotaFiscal(@PathVariable int id) {
        return notaFiscalService.buscarPorId(id);
    }

    @GetMapping
    public List<NotaFiscal> listarTodasNotasFiscais() {
        return notaFiscalService.listarTodas();
    }

    @PutMapping("/{id}")
    public NotaFiscal atualizarNotaFiscal(@PathVariable int id, @RequestBody NotaFiscal notaFiscal) {
        notaFiscal.setChave(id);
        return notaFiscalService.atualizar(notaFiscal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarNotaFiscal(@PathVariable int id) {
        notaFiscalService.delete(id);
    }
}
