package com.fabricaagricola.bdfabrica.service;

import com.fabricaagricola.bdfabrica.model.Lote;
import com.fabricaagricola.bdfabrica.repository.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoteService {

    private final LoteRepository loteRepository;

    @Autowired
    public LoteService(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    public Lote salvar(Lote lote) {
        return loteRepository.save(lote);
    }

    public Optional<Lote> buscarPorCodigo(String codigo) {
        return loteRepository.findByNumero(codigo);
    }

    public List<Lote> listarTodos() {
        return loteRepository.findAll();
    }

    public Lote atualizar(Lote lote) {
        return loteRepository.update(lote);
    }

    public void deletar(String codigo) {
        loteRepository.delete(codigo);
    }
}
