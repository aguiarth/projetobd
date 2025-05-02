package com.fabricaagricola.bdfabrica.model;

public class Vincula {
	private String cnpj;
	private String codigo;
	
	public Vincula() {}

    public Vincula(String cnpj, String codigo) {
        this.cnpj = cnpj;
        this.codigo = codigo;
    }
    
    public String getCnpj() {
    	return cnpj;
    }
    
    public void setCnpj(String cnpj) {
    	this.cnpj = cnpj;
    }
    
    public String getCodigo() {
    	return codigo;
    }
    
    public void setCodigo(String codigo) {
    	this.codigo = codigo;
    }
}