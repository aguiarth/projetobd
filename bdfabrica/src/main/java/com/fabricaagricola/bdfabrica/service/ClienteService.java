package com.fabricaagricola.bdfabrica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabricaagricola.bdfabrica.model.Cliente;
import com.fabricaagricola.bdfabrica.repository.ClienteRepository;

@Service
public class ClienteService {
	private ClienteRepository clienteRepository;
	
	@Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
	
	public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarPorCnpj(String cnpj) {
        return clienteRepository.findByCnpj(cnpj);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente atualizar(Cliente cliente) {
        return clienteRepository.update(cliente);
    }

    public void excluir(String cnpj) {
    	clienteRepository.delete(cnpj);
    }
}
