package com.fabricaagricola.bdfabrica.model;

public class Contem {
	private String numero;
	private int idProduto;
	
	public Contem() {}
	
	public Contem(String numero, int idProduto) {
		this.numero = numero;
		this.idProduto = idProduto;
	}
	
	public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public int getIdProduto() {
	    return idProduto;
	}

	public void setIdProduto(int idProduto) {
	    this.idProduto = idProduto;
	}
}
