package com.fabricaagricola.bdfabrica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Possui")
public class Possui {

    @EmbeddedId
    private PossuiId id;

    @ManyToOne
    @MapsId("cnpj")
    @JoinColumn(name = "cnpj")
    private Cliente cliente;

    @ManyToOne
    @MapsId("idConta")
    @JoinColumn(name = "id_conta")
    private ContaReceber contaReceber;

    // Construtor padrão
    public Possui() {}

    // Construtor completo
    public Possui(PossuiId id, Cliente cliente, ContaReceber contaReceber) {
        this.id = id;
        this.cliente = cliente;
        this.contaReceber = contaReceber;
    }

    // Getters e Setters
    public PossuiId getId() {
        return id;
    }

    public void setId(PossuiId id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ContaReceber getContaReceber() {
        return contaReceber;
    }

    public void setContaReceber(ContaReceber contaReceber) {
        this.contaReceber = contaReceber;
    }
}
