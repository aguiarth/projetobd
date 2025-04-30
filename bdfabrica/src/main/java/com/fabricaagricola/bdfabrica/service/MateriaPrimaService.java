package com.fabricaagricola.bdfabrica.service;

import com.fabricaagricola.bdfabrica.model.MateriaPrima;
import com.fabricaagricola.bdfabrica.repository.MateriaPrimaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaPrimaService {

    private final MateriaPrimaRepository materiaPrimaRepository;

    @Autowired
    public MateriaPrimaService(MateriaPrimaRepository materiaPrimaRepository) {
        this.materiaPrimaRepository = materiaPrimaRepository;
    }

    public MateriaPrima criarMateriaPrima(MateriaPrima materiaPrima) {
        return materiaPrimaRepository.save(materiaPrima);
    }

    public Optional<MateriaPrima> buscarPorId(int id) {
        return materiaPrimaRepository.findById(id);
    }

    public List<MateriaPrima> listarTodas() {
        return materiaPrimaRepository.findAll();
    }

    public MateriaPrima atualizarMateriaPrima(MateriaPrima materiaPrima) {
        return materiaPrimaRepository.update(materiaPrima);
    }

    public void deletarMateriaPrima(int id) {
    	materiaPrimaRepository.delete(id);
    }
}
