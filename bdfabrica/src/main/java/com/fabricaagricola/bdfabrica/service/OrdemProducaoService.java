package com.fabricaagricola.bdfabrica.service;

import com.fabricaagricola.bdfabrica.model.OrdemProducao;
import com.fabricaagricola.bdfabrica.repository.OrdemProducaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrdemProducaoService {
	private final OrdemProducaoRepository repository;

	@Autowired
	public OrdemProducaoService(OrdemProducaoRepository repository) {
		this.repository = repository;
	}

	public OrdemProducao salvar(OrdemProducao ordemProducao) {
		OrdemProducao criada = repository.save(ordemProducao);

		if (ordemProducao.getIdOrdemDependente() != null) {
			Optional<OrdemProducao> ordemDependenteOpt = buscarPorId(ordemProducao.getIdOrdemDependente());
			if (ordemDependenteOpt.isPresent()) {
				OrdemProducao ordemDependente = ordemDependenteOpt.get();
				ordemDependente.setIdOrdemRequisitada(criada.getIdOrdem());
				atualizar(ordemDependente);
			}
		}

		return criada;
	}

	public List<OrdemProducao> listarTodas() {
		return repository.findAll();
	}

	public Optional<OrdemProducao> buscarPorId(int id) {
		return repository.findById(id);
	}

	public OrdemProducao atualizar(OrdemProducao ordemProducao) {
		OrdemProducao atualizada = repository.update(ordemProducao);

		if (ordemProducao.getIdOrdemDependente() != null) {
			int idDependente = ordemProducao.getIdOrdemDependente();

			Optional<OrdemProducao> ordemDependenteOpt = buscarPorId(idDependente);

			ordemDependenteOpt.ifPresent(ordemDependente -> {
				ordemDependente.setIdOrdemRequisitada(ordemProducao.getIdOrdem());
				repository.update(ordemDependente);
			});
		}

		return atualizada;
	}

	public void deletar(int id) {
		repository.delete(id);
	}

	// METODOS AUXILIARES

	// obter ordem dependente completa (se necessário)
	public Optional<OrdemProducao> obterOrdemDependenteCompleta(OrdemProducao ordem) {
		if (ordem.getIdOrdemDependente() != null) {
			return repository.findById(ordem.getIdOrdemDependente());
		}
		return Optional.empty();
	}

	// obter ordem requisitada completa (se necessário)
	public Optional<OrdemProducao> obterOrdemRequisitadaCompleta(OrdemProducao ordem) {
		if (ordem.getIdOrdemRequisitada() != null) {
			return repository.findById(ordem.getIdOrdemRequisitada());
		}
		return Optional.empty();
	}
}