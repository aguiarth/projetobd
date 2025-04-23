package com.fabricaagricola.bdfabrica.service;

import com.fabricaagricola.bdfabrica.model.Conta;
import com.fabricaagricola.bdfabrica.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

	private final ContaRepository contaRepository;
	
	@Autowired
	public ContaService(ContaRepository contaRepository) {
		this.contaRepository = contaRepository;
	}
	
	public Conta salvar(Conta conta) {
		return contaRepository.save(conta);
	}
	
	public Optional<Conta> buscarPorId(int id){
		return contaRepository.findById(id);
	}
	
	public List<Conta> listarTodos(){
		return contaRepository.findAll();
	}
	
	public Conta atualizar(Conta conta) {
		return contaRepository.update(conta);
	}
	
	public void excluir(int id) {
		contaRepository.delete(id);
	}
}
