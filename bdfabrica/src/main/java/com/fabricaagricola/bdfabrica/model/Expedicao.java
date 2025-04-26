package com.fabricaagricola.bdfabrica.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fabricaagricola.bdfabrica.enums.StatusExpedicao;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Expedicao {
	private int idExpedicao;
	private LocalDate dataExpedicao;
	private LocalTime horaExpedicao;
	private StatusExpedicao status;
	
	public Expedicao() {
		this.dataExpedicao = LocalDate.now(); // só usar se for algo gerado automaticamente durante a criação
		this.horaExpedicao = LocalTime.now();
	}
	
	public Expedicao(int idExpedicao, LocalDate dataExpedicao, LocalTime horaExpedicao, StatusExpedicao status) {
		this.idExpedicao = idExpedicao;
		this.dataExpedicao = (dataExpedicao == null) ? LocalDate.now() : dataExpedicao;
		this.horaExpedicao = (horaExpedicao == null) ? LocalTime.now() : horaExpedicao;
		this.status = status;
	}
	
	public int getIdExpedicao() {
	    return idExpedicao;
	}

	public void setIdExpedicao(int idExpedicao) {
	    this.idExpedicao = idExpedicao;
	}

	public LocalDate getDataExpedicao() {
	    return dataExpedicao;
	}

	public void setDataExpedicao(LocalDate dataExpedicao) {
	    this.dataExpedicao = dataExpedicao;
	}
	
	@JsonFormat(pattern = "HH:mm:ss")
	public LocalTime getHoraExpedicao() {
	    return horaExpedicao;
	}

	public void setHoraExpedicao(LocalTime horaExpedicao) {
	    this.horaExpedicao = horaExpedicao;
	}

	public StatusExpedicao getStatus() {
	    return status;
	}

	public void setStatus(StatusExpedicao status) {
	    this.status = status;
	}

}
