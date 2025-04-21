package com.fabricaagricola.bdfabrica.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Pedido")
public class Pedido {

    @Id
    @Column(length = 45)
    private String numero;

    @ManyToOne
    @JoinColumn(name = "chave", referencedColumnName = "chave")
    private NotaFiscal notaFiscal;

    @Column
    private LocalDate dataEmissao;

    @Column
    private float valorTotal;

    @Column(length = 45)
    private String status;

    @Column(length = 45)
    private String formaPagamento;

    // Construtor padrão
    public Pedido() {}

    // Construtor completo
    public Pedido(String numero, NotaFiscal notaFiscal, LocalDate dataEmissao, float valorTotal, String status, String formaPagamento) {
        this.numero = numero;
        this.notaFiscal = notaFiscal;
        this.dataEmissao = dataEmissao;
        this.valorTotal = valorTotal;
        this.status = status;
        this.formaPagamento = formaPagamento;
    }

    // Getters e Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
