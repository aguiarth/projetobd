package com.fabricaagricola.bdfabrica.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Contas")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Conta {

    @Id
    @Column(name = "IdConta")
    private int idConta;

    @Column(name = "dataEmissao")
    private LocalDate dataEmissao;

    @Column(name = "dataVencimento")
    private LocalDate dataVencimento;

    @Column(name = "valorTotal")
    private float valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusConta status;

    public Conta() {}

    public Conta(int idConta, LocalDate dataEmissao, LocalDate dataVencimento, float valorTotal, StatusConta status) {
        this.idConta = idConta;
        this.dataEmissao = dataEmissao;
        this.dataVencimento = dataVencimento;
        this.valorTotal = valorTotal;
        this.status = status;
    }

    public int getIdConta() {
        return idConta;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusConta getStatus() {
        return status;
    }

    public void setStatus(StatusConta status) {
        this.status = status;
    }

    public abstract void processarConta();
}
