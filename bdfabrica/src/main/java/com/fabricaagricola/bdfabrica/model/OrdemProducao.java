package com.fabricaagricola.bdfabrica.model;

import java.time.LocalDate;

public class OrdemProducao {
	private int idOrdem;
	private Integer idOrdemDependente;
	private Integer idOrdemRequisitada;
	private String produtoFabricado;
	private int quantidadeProduto;
	private LocalDate dataInicio;
	private LocalDate dataFinal;
	private String descricao;

	public OrdemProducao() {
		this.dataInicio = LocalDate.now();
	}

	public OrdemProducao(int idOrdem, Integer idOrdemDependente, Integer idOrdemRequisitada, String produtoFabricado,
			int quantidadeProduto, LocalDate dataInicio, LocalDate dataFinal, String descricao) {
		this.idOrdem = idOrdem;
		this.idOrdemDependente = idOrdemDependente;
		this.idOrdemRequisitada = idOrdemRequisitada;
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

	public Integer getIdOrdemDependente() {
		return idOrdemDependente;
	}

	public void setIdOrdemDependente(Integer idOrdemDependente) {
		this.idOrdemDependente = idOrdemDependente;
	}

	public Integer getIdOrdemRequisitada() {
		return idOrdemRequisitada;
	}

	public void setIdOrdemRequisitada(Integer idOrdemRequisitada) {
		this.idOrdemRequisitada = idOrdemRequisitada;
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