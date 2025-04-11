package com.fabricaagricola.bdfabrica.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "Receber")
public class ContaReceber extends Conta {

    @ManyToOne
    @JoinTable(
        name = "Possui",
        joinColumns = @JoinColumn(name = "IdConta"),
        inverseJoinColumns = @JoinColumn(name = "cnpj")
    )
    private Cliente cliente;

    public ContaReceber() {}

    public ContaReceber(int idConta, LocalDate dataEmissao, LocalDate dataVencimento,
                        float valorTotal, StatusConta status, Cliente cliente) {
        super(idConta, dataEmissao, dataVencimento, valorTotal, status);
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public void processarConta() {
        if (getStatus() == StatusConta.PENDENTE) {
            setStatus(StatusConta.PAGO);
        }
    }
}
