package com.fabricaagricola.bdfabrica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabricaagricola.bdfabrica.model.Realiza;
import com.fabricaagricola.bdfabrica.repository.RealizaRepository;

@Service
public class RealizaService {
	
	private final RealizaRepository realizaRepository;
	
	@Autowired
    public RealizaService(RealizaRepository realizaRepository) {
        this.realizaRepository = realizaRepository;
    }
	
	public void salvarRelacionamento(Realiza realiza) {
	    realizaRepository.save(realiza);
	}
	
	public List<Realiza> listarTodos() {
	    return realizaRepository.findAll();
	}
	
	public List<Realiza> buscarPorCnpj(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("CNPJ não pode ser nulo ou vazio.");
        }
        return realizaRepository.findByCnpj(cnpj);
    }
	
	public Realiza buscarRelacionamento(String cnpj, String numero) {
        if (cnpj == null || cnpj.isBlank() || numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("CNPJ e numero devem ser informados.");
        }
        return realizaRepository.findByCnpjAndNumero(cnpj, numero);
    }
	
	public void deletarRelacionamento(String cnpj, String numero) {
        if (cnpj == null || cnpj.isBlank() || numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("CNPJ e numero são obrigatórios para exclusão.");
        }
        realizaRepository.delete(cnpj, numero);
    }
}
