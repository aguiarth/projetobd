package com.fabricaagricola.bdfabrica.model;

public class Consome {
	private int idMateriaPrima;
	private int idOrdem;
	
	public Consome() {}
	
	public Consome(int idMateriaPrima, int idOrdem) {
		this.idMateriaPrima = idMateriaPrima;
		this.idOrdem = idOrdem;
	}
	
	public int getIdMateriaPrima() {
		return idMateriaPrima;
	}
	
	public void setIdMateriaPrima(int idMateriaPrima) {
		this.idMateriaPrima = idMateriaPrima;
	}
	
	public int getIdOrdem() {
		return idOrdem;
	}
	
	public void setIdOrdem(int idOrdem) {
		this.idOrdem = idOrdem;
	}
}
