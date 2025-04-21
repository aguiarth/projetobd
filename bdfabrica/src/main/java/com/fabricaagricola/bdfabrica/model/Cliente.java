package com.fabricaagricola.bdfabrica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Cliente")
public class Cliente {

    @Id
    @Column(name = "cnpj", length = 45)
    private String cnpj;

    @Column(name = "razao_social", length = 45)
    private String razaoSocial;

    @Column(name = "rua", length = 45)
    private String rua;

    @Column(name = "numero", length = 45)
    private String numero;

    @Column(name = "cidade", length = 45)
    private String cidade;

    @Column(name = "cep", length = 45)
    private String cep;

    @Column(name = "telefone_pessoal", length = 45)
    private String telefonePessoal;

    @Column(name = "telefone_residencial", length = 45)
    private String telefoneResidencial;

    @Column(name = "email", length = 45)
    private String email;

    // Construtor padrão
    public Cliente() {}

    // Construtor completo
    public Cliente(String cnpj, String razaoSocial, String rua, String numero,
                   String cidade, String cep, String telefonePessoal,
                   String telefoneResidencial, String email) {
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.cep = cep;
        this.telefonePessoal = telefonePessoal;
        this.telefoneResidencial = telefoneResidencial;
        this.email = email;
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

    public String getTelefonePessoal() {
        return telefonePessoal;
    }

    public void setTelefonePessoal(String telefonePessoal) {
        this.telefonePessoal = telefonePessoal;
    }

    public String getTelefoneResidencial() {
        return telefoneResidencial;
    }

    public void setTelefoneResidencial(String telefoneResidencial) {
        this.telefoneResidencial = telefoneResidencial;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
