package com.fabricaagricola.bdfabrica.model;

import java.time.LocalDate;
import com.fabricaagricola.bdfabrica.enums.StatusConta;

public class Conta {

	private int idConta;
	private int idFinanceiro;
	private LocalDate dataEmissao;
	private LocalDate dataVencimento;
	private float valorTotal;
	private StatusConta status;
	
	public Conta() {
		this.dataEmissao = LocalDate.now();
	}
	
	public Conta(int idConta, int idFinanceiro, LocalDate dataEmissao, LocalDate dataVencimento, float valorTotal, StatusConta status) {
		this.idConta = idConta;
		this.idFinanceiro = idFinanceiro;
		this.dataEmissao = (dataEmissao == null) ? LocalDate.now() : dataEmissao;
		this.dataVencimento = dataVencimento;
		this.valorTotal = valorTotal;
		this.status = status;
	}
	
	public int getIdConta() {
		return idConta;
	}
	
	public void setIdConta(int idConta) {
		this.idConta = idConta;
	}
	
	public int getIdFinanceiro() {
		return idFinanceiro;
	}
	
	public void setIdFinanceiro(int idFinanceiro) {
		this.idFinanceiro = idFinanceiro;
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
	
}
