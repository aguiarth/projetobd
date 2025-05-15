package com.fabricaagricola.bdfabrica.model;

public class Realiza {
	private String cnpj;
	private String numero;
	
	public Realiza() {}
	
	public Realiza(String cnpj, String numero) {
		this.cnpj = cnpj;
		this.numero = numero;
	}
	
    public String getCnpj() {
    	return cnpj;
    }
    
    public void setCnpj(String cnpj) {
    	this.cnpj = cnpj;
    }
    
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
