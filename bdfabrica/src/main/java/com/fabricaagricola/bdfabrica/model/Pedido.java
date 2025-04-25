package com.fabricaagricola.bdfabrica.model;

import java.time.LocalDate;

import com.fabricaagricola.bdfabrica.enums.CondicoesPagamento;
import com.fabricaagricola.bdfabrica.enums.StatusGeral;

public class Pedido {
	private String numero;
	private LocalDate dataEmissao;
	private float valorTotal;
	private StatusGeral status;
	private CondicoesPagamento formaPagamento;
	
	public Pedido() {
		this.dataEmissao = LocalDate.now();
	}
	
	public Pedido(String numero, LocalDate dataEmissao, float valorTotal, StatusGeral status, CondicoesPagamento formaPagamento) {
		this.numero = numero;
		this.dataEmissao = (dataEmissao == null) ? LocalDate.now() : dataEmissao;
		this.valorTotal = valorTotal;
		this.status = status;
		this.formaPagamento = formaPagamento;
	}
	
	public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public StatusGeral getStatus() {
        return status;
    }

    public void setStatus(StatusGeral status) {
        this.status = status;
    }

    public CondicoesPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(CondicoesPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
