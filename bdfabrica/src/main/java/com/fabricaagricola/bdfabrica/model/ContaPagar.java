package com.fabricaagricola.bdfabrica.model;

public class ContaPagar {

	private int id_conta;
	private String cnpj;
	
	public ContaPagar(int id_conta, String cnpj) {
		this.id_conta = id_conta;
		this.cnpj = cnpj;
	}
	
	public int getIdConta() {
		return id_conta;
	}
	
	public void setIdConta(int id_conta) {
		this.id_conta = id_conta;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
}
