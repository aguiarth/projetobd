package com.fabricaagricola.bdfabrica.model;

public class Envia {
	private String numero;
	private int idExpedicao;
	
	public Envia() {}
	
	public Envia(String numero, int idExpedicao) {
		this.numero = numero;
		this.idExpedicao = idExpedicao;
	}
	
	public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public int getIdExpedicao() {
	    return idExpedicao;
	}

	public void setIdExpedicao(int idExpedicao) {
	    this.idExpedicao = idExpedicao;
	}
}
