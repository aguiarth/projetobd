package com.fabricaagricola.bdfabrica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabricaagricola.bdfabrica.model.Expedicao;
import com.fabricaagricola.bdfabrica.repository.ExpedicaoRepository;

@Service
public class ExpedicaoService {
	private ExpedicaoRepository expedicaoRepository;
	
	@Autowired
	public ExpedicaoService(ExpedicaoRepository expedicaoRepository) {
		this.expedicaoRepository = expedicaoRepository;
	}
	
	public Expedicao salvar(Expedicao expedicao) {
		return expedicaoRepository.save(expedicao);
	}
	
	public Optional<Expedicao> buscarPorId(int id){
		return expedicaoRepository.findById(id);
	}
	
	public List<Expedicao> listarTodos(){
		return expedicaoRepository.findAll();
	}
	
	public Expedicao atualizar(Expedicao expedicao) {
		return expedicaoRepository.update(expedicao);
	}
	
	public void excluir(int id) {
		expedicaoRepository.delete(id);
	}
}
