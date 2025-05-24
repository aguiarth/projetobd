package com.fabricaagricola.bdfabrica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabricaagricola.bdfabrica.model.EntregaTransporte;
import com.fabricaagricola.bdfabrica.repository.EntregaTransporteRepository;
import com.fabricaagricola.bdfabrica.repository.ExpedicaoRepository;

@Service
public class EntregaTransporteService {

    private EntregaTransporteRepository entregaRepo;
    @Autowired
    private ExpedicaoRepository expedicaoRepo;

    @Autowired
    public EntregaTransporteService(EntregaTransporteRepository entregaRepo) {
        this.entregaRepo = entregaRepo;
    }
    
    public EntregaTransporte salvar(EntregaTransporte entrega) {
        if (!expedicaoRepo.findById(entrega.getIdExpedicao()).isPresent()) {
            throw new IllegalArgumentException("ID de Expedição inválido.");
        }
        return entregaRepo.save(entrega);
    }

    public Optional<EntregaTransporte> buscarPorNumero(String numeroEntrega) {
        return entregaRepo.findByNumero(numeroEntrega);
    }

    public List<EntregaTransporte> listarTodas() {
        return entregaRepo.findAll();
    }

    public EntregaTransporte atualizar(String numeroEntrega, EntregaTransporte entrega) {
        if (!entregaRepo.findByNumero(numeroEntrega).isPresent()) {
            throw new IllegalArgumentException("Entrega com número informado não encontrada.");
        }
        if (!expedicaoRepo.findById(entrega.getIdExpedicao()).isPresent()) {
            throw new IllegalArgumentException("ID de Expedição inválido.");
        }
        entrega.setNumeroEntrega(numeroEntrega);
        return entregaRepo.update(entrega);
    }


    public void deletar(String numeroEntrega) {
        entregaRepo.delete(numeroEntrega);
    }
}
