package com.fabricaagricola.bdfabrica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabricaagricola.bdfabrica.model.Consome;
import com.fabricaagricola.bdfabrica.repository.ConsomeRepository;

@Service
public class ConsomeService {
    private final ConsomeRepository consomeRepository;

    @Autowired
    public ConsomeService(ConsomeRepository consomeRepository) {
        this.consomeRepository = consomeRepository;
    }

    public void salvarRelacionamento(Consome consome) {
        try {
            consomeRepository.save(consome);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro de validação: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao salvar o relacionamento: " + e.getMessage());
        }
    }

    public List<Consome> listarTodos() {
        try {
            return consomeRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar relacionamentos: " + e.getMessage());
        }
    }

    public List<Consome> buscarPorIdOrdem(int idOrdem) {
        if (idOrdem <= 0) {
            throw new IllegalArgumentException("idOrdem deve ser um número positivo.");
        }
        try {
            return consomeRepository.findByIdOrdem(idOrdem);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar por idOrdem: " + e.getMessage());
        }
    }

    public Consome buscarRelacionamento(int idOrdem, int idMateriaPrima) {
        if (idOrdem <= 0 || idMateriaPrima <= 0) {
            throw new IllegalArgumentException("idOrdem e idMateriaPrima devem ser números positivos.");
        }
        try {
            return consomeRepository.findByIdMateriaAndIdOrdem(idOrdem, idMateriaPrima);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar relacionamento: " + e.getMessage());
        }
    }

    public void deletarRelacionamento(int idOrdem, int idMateriaPrima) {
        if (idOrdem <= 0 || idMateriaPrima <= 0) {
            throw new IllegalArgumentException("idOrdem e idMateriaPrima são obrigatórios para exclusão.");
        }
        try {
            consomeRepository.delete(idOrdem, idMateriaPrima);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao deletar relacionamento: " + e.getMessage());
        }
    }
}
