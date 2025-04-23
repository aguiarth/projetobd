package com.fabricaagricola.bdfabrica.model;

import java.util.List;

public class Cliente {
    private String cnpj;
    private String razaoSocial;
    private String rua;
    private String numero;
    private String cidade;
    private String cep;
    private String email;
    private List<TelefoneCliente> telefones;
    // so colocar telefone no construtor caso a gnt queira q seja criado junto com cliente

    public Cliente(String cnpj, String razaoSocial, String rua, String numero, String cidade, String cep, String email, List<TelefoneCliente> telefones) {
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.cep = cep;
        this.email = email;
        this.telefones = telefones != null ? telefones : List.of();
    }

    public String getCnpjCliente() {
        return cnpj;
    }

    public void setCnpjCliente(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TelefoneCliente> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<TelefoneCliente> telefones) {
    	this.telefones = telefones != null ? telefones : List.of();
    }
}
