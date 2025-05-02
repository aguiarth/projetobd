package com.fabricaagricola.bdfabrica.service;

import com.fabricaagricola.bdfabrica.model.Vincula;
import com.fabricaagricola.bdfabrica.repository.VinculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VinculaService {

    private final VinculaRepository vinculaRepository;

    @Autowired
    public VinculaService(VinculaRepository vinculaRepository) {
        this.vinculaRepository = vinculaRepository;
    }

    public void salvarRelacionamento(Vincula vincula) {
        try {
            vinculaRepository.save(vincula);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro de validação: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao salvar o relacionamento: " + e.getMessage());
        }
    }

    public List<Vincula> listarTodos() {
        try {
            return vinculaRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar relacionamentos: " + e.getMessage());
        }
    }

    public List<Vincula> buscarPorCnpj(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("CNPJ não pode ser nulo ou vazio.");
        }
        try {
            return vinculaRepository.findByCnpj(cnpj);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar por CNPJ: " + e.getMessage());
        }
    }

    public Vincula buscarRelacionamento(String cnpj, String codigo) {
        if (cnpj == null || cnpj.isBlank() || codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("CNPJ e Código devem ser informados.");
        }
        try {
            return vinculaRepository.findByCnpjAndCodigo(cnpj, codigo);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar relacionamento: " + e.getMessage());
        }
    }

    public void deletarRelacionamento(String cnpj, String codigo) {
        if (cnpj == null || cnpj.isBlank() || codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("CNPJ e Código são obrigatórios para exclusão.");
        }
        try {
            vinculaRepository.delete(cnpj, codigo);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao deletar relacionamento: " + e.getMessage());
        }
    }
}
