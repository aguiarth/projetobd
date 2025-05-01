package com.fabricaagricola.bdfabrica.service;

import com.fabricaagricola.bdfabrica.model.Gera;
import com.fabricaagricola.bdfabrica.repository.GeraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeraService {

    private final GeraRepository geraRepository;

    @Autowired
    public GeraService(GeraRepository geraRepository) {
        this.geraRepository = geraRepository;
    }

    public void salvarRelacionamento(Gera gera) {
        geraRepository.save(gera);
    }

    public List<Gera> listarTodos() {
        return geraRepository.findAll();
    }

    public List<Gera> buscarPorOrdem(int idOrdem) {
        return geraRepository.findByOrdem(idOrdem);
    }
    
    public Gera buscarRelacionamento(int idOrdem, int idProduto) {
        return geraRepository.findByOrdemAndProduto(idOrdem, idProduto);
    }

    public void deletarRelacionamento(int idOrdem, int idProduto) {
        geraRepository.delete(idOrdem, idProduto);
    }
}
