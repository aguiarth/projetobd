package com.fabricaagricola.bdfabrica.service;

import com.fabricaagricola.bdfabrica.model.Fornecedor;
import com.fabricaagricola.bdfabrica.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

	private final FornecedorRepository fornecedorRepository;
	
	@Autowired
	public FornecedorService(FornecedorRepository fornecedorRepository) {
		this.fornecedorRepository = fornecedorRepository;
	}
	
	public Fornecedor salvar(Fornecedor fornecedor) {
		return fornecedorRepository.save(fornecedor);
	}
	
	public Optional<Fornecedor> buscarPorCnpj(String cnpj){
		return fornecedorRepository.findByCnpj(cnpj);
	}
	
	public List<Fornecedor> listarTodos() {
		return fornecedorRepository.findAll();
	}
	
	public Fornecedor atualizar (Fornecedor fornecedor) {
		return fornecedorRepository.update(fornecedor);
	}
	
	public void excluir(String cnpj) {
		fornecedorRepository.delete(cnpj);
	}
	
}
