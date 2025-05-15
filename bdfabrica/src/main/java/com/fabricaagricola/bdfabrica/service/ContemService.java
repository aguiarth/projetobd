package com.fabricaagricola.bdfabrica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabricaagricola.bdfabrica.model.Contem;
import com.fabricaagricola.bdfabrica.repository.ContemRepository;

@Service
public class ContemService {
	private final ContemRepository contemRepository;
	
	@Autowired
    public ContemService(ContemRepository contemRepository) {
        this.contemRepository = contemRepository;
    }
	
	public void salvarRelacionamento(Contem contem) {
	    contemRepository.save(contem);
	}
	
	public List<Contem> listarTodos() {
	    return contemRepository.findAll();
	}
	
	public List<Contem> buscarPorNumero(String numero) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("numero não pode ser nulo ou vazio.");
        }
        return contemRepository.findByNumero(numero);
    }
	
	public Contem buscarRelacionamento(String numero, int idProduto) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("numero e idProduto devem ser informados.");
        }
        return contemRepository.findByNumeroAndId(numero, idProduto);
    }
	
	public void deletarRelacionamento(String numero, int idProduto) {
        if (idProduto <= 0 || numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("idProduto e numero são obrigatórios para exclusão.");
        }
        contemRepository.delete(numero, idProduto);
    }
}
