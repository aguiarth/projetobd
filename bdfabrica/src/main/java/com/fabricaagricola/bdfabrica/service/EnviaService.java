package com.fabricaagricola.bdfabrica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabricaagricola.bdfabrica.model.Envia;
import com.fabricaagricola.bdfabrica.repository.EnviaRepository;

@Service
public class EnviaService {
private final EnviaRepository enviaRepository;
	
	@Autowired
    public EnviaService(EnviaRepository enviaRepository) {
        this.enviaRepository = enviaRepository;
    }
	
	public void salvarRelacionamento(Envia envia) {
	    enviaRepository.save(envia);
	}
	
	public List<Envia> listarTodos() {
	    return enviaRepository.findAll();
	}
	
	public List<Envia> buscarPorId(int idExpedicao) {
        if (idExpedicao <= 0) {
            throw new IllegalArgumentException("id não pode ser nulo");
        }
        return enviaRepository.findById(idExpedicao);
    }
	
	public Envia buscarRelacionamento(int idExpedicao, String numero) {
		if (numero == null || numero.isBlank()) {
	        throw new IllegalArgumentException("Número deve ser informado.");
	    }
	    return enviaRepository.findByIdAndNumero(idExpedicao, numero);
    }
	
	public void deletarRelacionamento(int idExpedicao, String numero) {
        if (idExpedicao <= 0 || numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("idExpedicao e número são obrigatórios para exclusão.");
        }
        enviaRepository.delete(idExpedicao, numero);
    }
}
