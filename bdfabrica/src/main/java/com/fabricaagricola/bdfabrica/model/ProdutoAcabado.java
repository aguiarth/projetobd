package com.fabricaagricola.bdfabrica.model;

import java.time.LocalDate;

public class ProdutoAcabado {
	private int idProduto;
	private String descricao;
	private LocalDate dataFinalizacao;
	
	public ProdutoAcabado(){
		this.dataFinalizacao = LocalDate.now();
	}
	
	public ProdutoAcabado(int idProduto, String descricao, LocalDate dataFinalizacao) {
		this.idProduto = idProduto;
		this.descricao = descricao;
		this.dataFinalizacao = (dataFinalizacao == null) ? LocalDate.now() : dataFinalizacao;
	}
	
	public int getIdProduto() {
	    return idProduto;
	}

	public void setIdProduto(int idProduto) {
	    this.idProduto = idProduto;
	}

	public String getDescricao() {
	    return descricao;
	}

	public void setDescricao(String descricao) {
	    this.descricao = descricao;
	}

	public LocalDate getDataFinalizacao() {
	    return dataFinalizacao;
	}

	public void setDataFinalizacao(LocalDate dataFinalizacao) {
	    this.dataFinalizacao = dataFinalizacao;
	}

}
