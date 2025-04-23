package com.fabricaagricola.bdfabrica.service;

import com.fabricaagricola.bdfabrica.model.Financeiro;
import com.fabricaagricola.bdfabrica.repository.FinanceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinanceiroService {

    private final FinanceiroRepository financeiroRepository;

    @Autowired
    public FinanceiroService(FinanceiroRepository financeiroRepository) {
        this.financeiroRepository = financeiroRepository;
    }

    public Financeiro salvar(Financeiro financeiro) {
        return financeiroRepository.save(financeiro);
    }

    public Optional<Financeiro> buscarPorId(int id) {
        return financeiroRepository.findById(id);
    }

    public List<Financeiro> listarTodos() {
        return financeiroRepository.findAll();
    }

    public Financeiro atualizar(Financeiro financeiro) {
        return financeiroRepository.update(financeiro);
    }

    public void excluir(int id) {
        financeiroRepository.delete(id);
    }
}
