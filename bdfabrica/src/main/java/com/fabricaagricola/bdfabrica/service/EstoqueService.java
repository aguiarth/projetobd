package com.fabricaagricola.bdfabrica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabricaagricola.bdfabrica.model.Estoque;
import com.fabricaagricola.bdfabrica.repository.EstoqueRepository;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public Estoque salvar(Estoque estoque) {
        return estoqueRepository.save(estoque);
    }

    public Estoque buscarPorId(int id) {
        Optional<Estoque> estoque = estoqueRepository.findById(id);
        return estoque.orElseThrow(() -> 
            new RuntimeException("Estoque com ID " + id + " não encontrado.")
        );
    }

    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    public Estoque atualizar(Estoque estoque) {
        return estoqueRepository.update(estoque);
    }

    public void deletar(int id) {
        estoqueRepository.delete(id);
    }
}
