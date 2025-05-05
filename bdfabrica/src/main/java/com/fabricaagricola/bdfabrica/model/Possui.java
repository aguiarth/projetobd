package com.fabricaagricola.bdfabrica.model;

public class Possui {
	private String cnpj;
	private int idConta;
	
	public Possui() {}
	
	public Possui(String cnpj, int idConta) {
		this.cnpj = cnpj;
		this.idConta = idConta;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public int getIdConta() {
		return idConta;
	}
	
	public void setIdConta(int idConta) {
		this.idConta = idConta;
	}
}
