package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.NotaFiscal;
import com.fabricaagricola.bdfabrica.repository.NotaFiscalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas-fiscais")
public class NotaFiscalController {

    @Autowired
    private NotaFiscalRepository notaFiscalRepository;

    // Listar todas as notas fiscais
    @GetMapping
    public List<NotaFiscal> listarTodas() {
        return notaFiscalRepository.findAll();
    }

    // Buscar nota fiscal por chave
    @GetMapping("/{chave}")
    public ResponseEntity<NotaFiscal> buscarPorChave(@PathVariable String chave) {
        return notaFiscalRepository.findById(chave)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar nova nota fiscal
    @PostMapping
    public NotaFiscal criar(@RequestBody NotaFiscal notaFiscal) {
        return notaFiscalRepository.save(notaFiscal);
    }

    // Atualizar nota fiscal existente
    @PutMapping("/{chave}")
    public ResponseEntity<NotaFiscal> atualizar(@PathVariable String chave, @RequestBody NotaFiscal notaFiscalAtualizada) {
        return notaFiscalRepository.findById(chave).map(notaFiscal -> {
            notaFiscal.setValorImposto(notaFiscalAtualizada.getValorImposto());
            notaFiscal.setDataEmissaoNota(notaFiscalAtualizada.getDataEmissaoNota());
            return ResponseEntity.ok(notaFiscalRepository.save(notaFiscal));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Deletar nota fiscal
    @DeleteMapping("/{chave}")
    public ResponseEntity<Void> deletar(@PathVariable String chave) {
        return notaFiscalRepository.findById(chave)
                .map(notaFiscal -> {
                    notaFiscalRepository.delete(notaFiscal);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
