package com.fabricaagricola.bdfabrica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabricaagricola.bdfabrica.model.Pedido;
import com.fabricaagricola.bdfabrica.repository.PedidoRepository;

@Service
public class PedidoService {
	private PedidoRepository pedidoRepository;
	
	@Autowired
	public PedidoService(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}
	
	public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> buscarPorNumero(String numero) {
        return pedidoRepository.findByNumero(numero);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido atualizar(Pedido pedido) {
        return pedidoRepository.update(pedido);
    }

    public void excluir(String numero) {
    	pedidoRepository.delete(numero);
    }
}
