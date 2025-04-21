package com.fabricaagricola.bdfabrica.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TelefoneCliente> telefones = new ArrayList<>();


    @Column(name = "email", length = 45)
    private String email;

    // Construtor padrão
    public Cliente() {}

    // Construtor completo
    public Cliente(String cnpj, String razaoSocial, String rua, String numero,
                   String cidade, String cep, List<TelefoneCliente> telefones, String email) {
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.cep = cep;
        this.telefones = telefones;
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

    public List<TelefoneCliente> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<TelefoneCliente> telefones) {
        this.telefones = telefones;
        if (telefones != null) {
            for (TelefoneCliente tel : telefones) {
                tel.setCliente(this); // mantém o vínculo reverso
            }
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void addTelefone(TelefoneCliente telefone) {
        if (telefone != null) {
            this.telefones.add(telefone);
            telefone.setCliente(this);  // Vincula o telefone ao cliente
        }
    }

    public void removeTelefone(TelefoneCliente telefone) {
        if (telefone != null) {
            this.telefones.remove(telefone);
            telefone.setCliente(null);  // Desvincula o telefone do cliente
        }
    }

}
