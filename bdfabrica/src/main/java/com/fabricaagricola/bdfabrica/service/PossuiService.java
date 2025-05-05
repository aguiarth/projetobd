package com.fabricaagricola.bdfabrica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabricaagricola.bdfabrica.model.Possui;
import com.fabricaagricola.bdfabrica.repository.PossuiRepository;

@Service
public class PossuiService {
	
	private final PossuiRepository possuiRepository;

    @Autowired
    public PossuiService(PossuiRepository possuiRepository) {
        this.possuiRepository = possuiRepository;
    }
    
    public void salvarRelacionamento(Possui possui) {
        try {
        	possuiRepository.save(possui);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro de validação: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao salvar o relacionamento: " + e.getMessage());
        }
    }

    public List<Possui> listarTodos() {
        return possuiRepository.findAll();
    }

    
    public List<Possui> buscarPorCnpj(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("CNPJ não pode ser nulo ou vazio.");
        }
        try {
            return possuiRepository.findByCnpj(cnpj);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar por CNPJ: " + e.getMessage());
        }
    }
    
    public Possui buscarRelacionamento(String cnpj, int idConta) {
        if (cnpj == null || cnpj.isBlank() || idConta <= 0) {
            throw new IllegalArgumentException("CNPJ e idConta devem ser informados.");
        }
        try {
            return possuiRepository.findByCnpjAndIdConta(cnpj, idConta);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar relacionamento: " + e.getMessage());
        }
    }
    
    public void deletarRelacionamento(String cnpj, int idConta) {
        if (cnpj == null || cnpj.isBlank() || idConta <= 0) {
            throw new IllegalArgumentException("CNPJ e idConta são obrigatórios para exclusão.");
        }
        try {
        	possuiRepository.delete(cnpj, idConta);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao deletar relacionamento: " + e.getMessage());
        }
    }
}
