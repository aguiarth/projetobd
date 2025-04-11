package com.fabricaagricola.bdfabrica.model;

import com.fabricaagricola.bdfabrica.enums.CondicoesPagamento;
import jakarta.persistence.*;

@Entity
@Table(name = "Fornecedor")
public class Fornecedor {

    @Id
    @Column(length = 45)
    private String cnpj;

    @Column(length = 45)
    private String razaoSocial;

    @Column(length = 45)
    private String endereco;

    @Column(length = 45)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "condicoesPagamento", length = 45)
    private CondicoesPagamento condicoesPagamento;

    // Construtor padrão
    public Fornecedor() {}

    // Construtor completo
    public Fornecedor(String cnpj, String razaoSocial, String endereco, String telefone, CondicoesPagamento condicoesPagamento) {
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.endereco = endereco;
        this.telefone = telefone;
        this.condicoesPagamento = condicoesPagamento;
    }

    // Getters e Setters
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public CondicoesPagamento getCondicoesPagamento() {
        return condicoesPagamento;
    }

    public void setCondicoesPagamento(CondicoesPagamento condicoesPagamento) {
        this.condicoesPagamento = condicoesPagamento;
    }
}
