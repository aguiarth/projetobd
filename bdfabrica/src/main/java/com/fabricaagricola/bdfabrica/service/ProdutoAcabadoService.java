package com.fabricaagricola.bdfabrica.service;

import com.fabricaagricola.bdfabrica.model.ProdutoAcabado;
import com.fabricaagricola.bdfabrica.repository.ProdutoAcabadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoAcabadoService {

    private final ProdutoAcabadoRepository produtoAcabadoRepository;

    @Autowired
    public ProdutoAcabadoService(ProdutoAcabadoRepository produtoAcabadoRepository) {
        this.produtoAcabadoRepository = produtoAcabadoRepository;
    }

    public ProdutoAcabado salvar(ProdutoAcabado produtoAcabado) {
        return produtoAcabadoRepository.save(produtoAcabado);
    }

    public Optional<ProdutoAcabado> buscarPorId(int id) {
        return produtoAcabadoRepository.findById(id);
    }

    public List<ProdutoAcabado> listarTodos() {
        return produtoAcabadoRepository.findAll();
    }

    public ProdutoAcabado atualizar(ProdutoAcabado produtoAcabado) {
        return produtoAcabadoRepository.update(produtoAcabado);
    }

    public void deletar(int id) {
        produtoAcabadoRepository.delete(id);
    }
}
