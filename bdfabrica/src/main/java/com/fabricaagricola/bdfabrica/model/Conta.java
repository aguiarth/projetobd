package com.fabricaagricola.bdfabrica.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fabricaagricola.bdfabrica.enums.StatusConta;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Contas")
public abstract class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdConta")
    private int idConta;

    @ManyToOne
    @JoinColumn(name = "IdFinanceiro") // nome exatamente como está no script SQL
    private Financeiro financeiro;

    @Column(name = "dataEmissao")
    private LocalDate dataEmissao;

    @Column(name = "dataVencimento")
    private LocalDate dataVencimento;

    @Column(name = "valorTotal")
    private float valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusConta status;

    // Construtores
    public Conta() {}

    public Conta(Financeiro financeiro, LocalDate dataEmissao, LocalDate dataVencimento, float valorTotal, StatusConta status) {
        this.financeiro = financeiro;
        this.dataEmissao = dataEmissao;
        this.dataVencimento = dataVencimento;
        this.valorTotal = valorTotal;
        this.status = status;
    }

    // Getters e Setters
    public int getIdConta() {
        return idConta;
    }

    public Financeiro getFinanceiro() {
        return financeiro;
    }

    public void setFinanceiro(Financeiro financeiro) {
        this.financeiro = financeiro;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
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

    // Método abstrato para subclasses implementarem
    public abstract void processarConta();
}
