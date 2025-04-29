package com.fabricaagricola.bdfabrica.service;

import com.fabricaagricola.bdfabrica.model.OrdemProducao;
import com.fabricaagricola.bdfabrica.repository.OrdemProducaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdemProducaoService {

    private final OrdemProducaoRepository ordemProducaoRepository;

    @Autowired
    public OrdemProducaoService(OrdemProducaoRepository ordemProducaoRepository) {
        this.ordemProducaoRepository = ordemProducaoRepository;
    }

    public OrdemProducao salvarComRelacionamento(OrdemProducao novaOrdem) {
        if (novaOrdem.getIdDependente() != null) {
            Optional<OrdemProducao> ordemDependente = ordemProducaoRepository.findById(novaOrdem.getIdDependente());
            if (!ordemDependente.isPresent()) {
                throw new RuntimeException("A ordem dependente com ID " + novaOrdem.getIdDependente() + " não existe.");
            }
        }

        OrdemProducao salva = salvar(novaOrdem);

        if (salva.getIdDependente() != null) {
            Optional<OrdemProducao> ordemDependida = buscarPorId(salva.getIdDependente());
            ordemDependida.ifPresent(ordem -> {
                ordem.setIdRequisitado(salva.getIdOrdem());
                atualizar(ordem);
            });
        }

        return salva;
    }

    public OrdemProducao salvar(OrdemProducao ordemProducao) {
        return ordemProducaoRepository.save(ordemProducao);
    }

    public Optional<OrdemProducao> buscarPorId(int id) {
        return ordemProducaoRepository.findById(id);
    }

    public List<OrdemProducao> listarTodas() {
        return ordemProducaoRepository.findAll();
    }

    public OrdemProducao atualizar(OrdemProducao ordemProducao) {
        sincronizarRelacionamento(ordemProducao);
        return ordemProducaoRepository.update(ordemProducao);
    }

    private void sincronizarRelacionamento(OrdemProducao ordemAtual) {
        if (ordemAtual.getIdDependente() != null) {
            Optional<OrdemProducao> ordemDependente = buscarPorId(ordemAtual.getIdDependente());
            ordemDependente.ifPresent(ordem -> {
                ordem.setIdRequisitado(ordemAtual.getIdOrdem());
                atualizar(ordem);
            });
        }
    }

    public void deletar(int id) {
        Optional<OrdemProducao> ordemParaDeletar = buscarPorId(id);
        if (ordemParaDeletar.isPresent()) {
            OrdemProducao ordem = ordemParaDeletar.get();
            if (ordem.getIdDependente() != null) {
                Optional<OrdemProducao> dependente = buscarPorId(ordem.getIdDependente());
                dependente.ifPresent(dep -> dep.setIdRequisitado(null));
            }
        }

        ordemProducaoRepository.delete(id);
    }
}
