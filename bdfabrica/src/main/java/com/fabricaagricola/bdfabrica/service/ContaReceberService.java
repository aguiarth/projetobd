package com.fabricaagricola.bdfabrica.service;

import com.fabricaagricola.bdfabrica.model.ContaReceber;
import com.fabricaagricola.bdfabrica.repository.ContaReceberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaReceberService {

	private final ContaReceberRepository contaReceberRepository;
	
	@Autowired
	public ContaReceberService (ContaReceberRepository contaReceberRepository) {
		this.contaReceberRepository = contaReceberRepository;
	}
	
	public ContaReceber salvar (ContaReceber contaReceber) {
		return contaReceberRepository.save(contaReceber);
	}
	
	public Optional<ContaReceber> buscarPorId(int id){
		return contaReceberRepository.findById(id);
	}
	
	public List<ContaReceber> listarTodos(){
		return contaReceberRepository.findAll();
	}
	
	public void excluir(int id) {
		contaReceberRepository.delete(id);
	}
	
}
