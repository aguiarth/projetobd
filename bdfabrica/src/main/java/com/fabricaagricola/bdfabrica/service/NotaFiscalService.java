package com.fabricaagricola.bdfabrica.service;

import com.fabricaagricola.bdfabrica.model.NotaFiscal;
import com.fabricaagricola.bdfabrica.repository.NotaFiscalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotaFiscalService {

    private final NotaFiscalRepository notaFiscalRepository;

    @Autowired
    public NotaFiscalService(NotaFiscalRepository notaFiscalRepository) {
        this.notaFiscalRepository = notaFiscalRepository;
    }

    public NotaFiscal salvarNotaFiscal(NotaFiscal notaFiscal) {
        if (notaFiscal.getValorImposto() < 0) {
            throw new IllegalArgumentException("O valor do imposto não pode ser negativo");
        }
        return notaFiscalRepository.save(notaFiscal);
    }

    public Optional<NotaFiscal> buscarPorId(int id) {
        return notaFiscalRepository.findById(id);
    }

    public List<NotaFiscal> listarTodas() {
        return notaFiscalRepository.findAll();
    }

    public NotaFiscal atualizar(NotaFiscal notaFiscal) {
        if (notaFiscal.getChave() == 0) {
            throw new IllegalArgumentException("A chave da nota fiscal não pode ser 0");
        }
        return notaFiscalRepository.update(notaFiscal);
    }

    public void delete(int id) {
        Optional<NotaFiscal> notaFiscal = buscarPorId(id);
        if (!notaFiscal.isPresent()) {
            throw new IllegalArgumentException("Nota fiscal com a chave " + id + " não encontrada.");
        }
        notaFiscalRepository.delete(id);
    }
}
