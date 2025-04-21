package com.fabricaagricola.bdfabrica.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@IdClass(TelefoneClienteId.class)
@Table(name = "Telefone_cliente")
public class TelefoneCliente {

    @Id
    @ManyToOne
    @JoinColumn(name = "cnpj", nullable = false)
    @JsonBackReference
    private Cliente cliente;


    @Id
    @Column(name = "telefone_cliente", length = 45, nullable = false)
    private String telefone;

    // Construtores
    public TelefoneCliente() {}

    public TelefoneCliente(String telefone, Cliente cliente) {
        this.telefone = telefone;
        this.cliente = cliente;
    }

    // Getters e Setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}

