package com.fabricaagricola.bdfabrica.model;

import com.fabricaagricola.bdfabrica.enums.CondicoesPagamento;

public class Fornecedor {

	private String cnpj;
	private String razao_social;
	private String endereco;
	private String telefone;
	private CondicoesPagamento condicoes_pagamento;
	
	public Fornecedor(String cnpj, String razao_social, String endereco, String telefone, CondicoesPagamento condicoes_pagamento) {
		this.cnpj = cnpj;
		this.razao_social = razao_social;
		this.endereco = endereco;
		this.telefone = telefone;
		this.condicoes_pagamento = condicoes_pagamento;
	}
	
	public String getCnpj () {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getRazaoSocial () {
		return razao_social;
	}
	
	public void setRazaoSocial (String razao_social) {
		this.razao_social = razao_social;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco (String endereco) {
		this.endereco = endereco;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public CondicoesPagamento getCondicoesPagamento() {
		return condicoes_pagamento;
	}
	
	public void setCondicoesPagamento(CondicoesPagamento condicoes_pagamento) {
		this.condicoes_pagamento = condicoes_pagamento;
	}
	
}
