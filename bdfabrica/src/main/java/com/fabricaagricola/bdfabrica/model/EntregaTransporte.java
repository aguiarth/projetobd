package com.fabricaagricola.bdfabrica.model;

import java.time.LocalDate;

public class EntregaTransporte {
	private String numeroEntrega;
	private LocalDate dataPrevista;
	private LocalDate dataSaida;
	private LocalDate dataEntrega;	
	private int idExpedicao;
	
	public EntregaTransporte() {}
	
	public EntregaTransporte(String numeroEntrega, LocalDate dataPrevista, LocalDate dataSaida, LocalDate dataEntrega, int idExpedicao) {
		this.numeroEntrega = numeroEntrega;
		this.dataPrevista = dataPrevista;
		this.dataSaida = dataSaida;
		this.dataEntrega = dataEntrega;
		this.idExpedicao = idExpedicao;
	}
	
	public String getNumeroEntrega() {
	    return numeroEntrega;
	}

	public void setNumeroEntrega(String numeroEntrega) {
	    this.numeroEntrega = numeroEntrega;
	}

	public LocalDate getDataPrevista() {
	    return dataPrevista;
	}

	public void setDataPrevista(LocalDate dataPrevista) {
	    this.dataPrevista = dataPrevista;
	}

	public LocalDate getDataSaida() {
	    return dataSaida;
	}

	public void setDataSaida(LocalDate dataSaida) {
	    this.dataSaida = dataSaida;
	}

	public LocalDate getDataEntrega() {
	    return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
	    this.dataEntrega = dataEntrega;
	}

	public int getIdExpedicao() {
	    return idExpedicao;
	}

	public void setIdExpedicao(int idExpedicao) {
	    this.idExpedicao = idExpedicao;
	}

}
