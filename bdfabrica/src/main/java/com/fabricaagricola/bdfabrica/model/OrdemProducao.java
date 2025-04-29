package com.fabricaagricola.bdfabrica.model;

import java.time.LocalDate;

public class OrdemProducao {
	private int idOrdem;
	private Integer idDependente; 	// pode ser nulo pq pode não depender de nenhuma outra ordem
	private Integer idRequisitado; 	// pode ser nulo pq pode não requisitar de nenhuma outra ordem
	private String produtoFabricado;
	private int quantidadeProduto;
	private LocalDate dataInicio;
	private LocalDate dataFinal;
	private String descricao;

	public OrdemProducao() {
		this.dataInicio = LocalDate.now();
	}

	public OrdemProducao(int idOrdem, Integer idDependente, Integer idRequisitado, String produtoFabricado,
			int quantidadeProduto, LocalDate dataInicio, LocalDate dataFinal, String descricao) {
		this.idOrdem = idOrdem;
		this.idDependente = idDependente;
		this.idRequisitado = idRequisitado;
		this.produtoFabricado = produtoFabricado;
		this.quantidadeProduto = quantidadeProduto;
		this.dataInicio = (dataInicio == null) ? LocalDate.now() : dataInicio;
		this.dataFinal = dataFinal;
		this.descricao = descricao;
	}

	public int getIdOrdem() {
		return idOrdem;
	}

	public void setIdOrdem(int idOrdem) {
		this.idOrdem = idOrdem;
	}

	public Integer getIdDependente() {
		return idDependente;
	}

	public void setIdDependente(Integer idDependente) {
		this.idDependente = idDependente;
	}

	public Integer getIdRequisitado() {
		return idRequisitado;
	}

	public void setIdRequisitado(Integer idRequisitado) {
		this.idRequisitado = idRequisitado;
	}

	public String getProdutoFabricado() {
		return produtoFabricado;
	}

	public void setProdutoFabricado(String produtoFabricado) {
		this.produtoFabricado = produtoFabricado;
	}

	public int getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(int quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
