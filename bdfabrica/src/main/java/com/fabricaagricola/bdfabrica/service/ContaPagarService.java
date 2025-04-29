package com.fabricaagricola.bdfabrica.service;

import com.fabricaagricola.bdfabrica.model.ContaPagar;
import com.fabricaagricola.bdfabrica.repository.ContaPagarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaPagarService {

	private final ContaPagarRepository contaPagarRepository;
	
	@Autowired
	public ContaPagarService (ContaPagarRepository contaPagarRepository) {
		this.contaPagarRepository = contaPagarRepository;
	}
	
	public ContaPagar salvar (ContaPagar contaPagar) {
		return contaPagarRepository.save(contaPagar);
	}
	
	public Optional<ContaPagar> buscarPorPk(int idConta, String cnpj){
		return contaPagarRepository.findByPk(idConta, cnpj);
	}
	
	public List<ContaPagar> listarTodos(){
		return contaPagarRepository.findAll();
	}
	
	public void excluir(int idConta, String cnpj) {
		contaPagarRepository.delete(idConta, cnpj);
	}
	
}
