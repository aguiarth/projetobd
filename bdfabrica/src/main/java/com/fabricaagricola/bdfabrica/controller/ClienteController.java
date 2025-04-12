package com.fabricaagricola.bdfabrica.controller;

import com.fabricaagricola.bdfabrica.model.Cliente;
import com.fabricaagricola.bdfabrica.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @PostMapping
    public Cliente criar(@RequestBody Cliente cliente) {
        return repository.save(cliente);
    }

    @GetMapping
    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<Cliente> buscarPorCnpj(@PathVariable String cnpj) {
        Optional<Cliente> cliente = repository.findById(cnpj);
        return cliente.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{cnpj}")
    public ResponseEntity<Cliente> atualizar(@PathVariable String cnpj, @RequestBody Cliente novoCliente) {
        return repository.findById(cnpj).map(cliente -> {
            cliente.setRazaoSocial(novoCliente.getRazaoSocial());
            cliente.setRua(novoCliente.getRua());
            cliente.setNumero(novoCliente.getNumero());
            cliente.setCidade(novoCliente.getCidade());
            cliente.setCep(novoCliente.getCep());
            cliente.setTelefonePessoal(novoCliente.getTelefonePessoal());
            cliente.setTelefoneResidencial(novoCliente.getTelefoneResidencial());
            cliente.setEmail(novoCliente.getEmail());
            return ResponseEntity.ok(repository.save(cliente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cnpj}")
    public ResponseEntity<Void> deletar(@PathVariable String cnpj) {
        if (repository.existsById(cnpj)) {
            repository.deleteById(cnpj);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
