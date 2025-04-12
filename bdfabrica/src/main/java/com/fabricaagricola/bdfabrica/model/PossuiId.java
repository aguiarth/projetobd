package com.fabricaagricola.bdfabrica.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class PossuiId implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cnpj;
    private int idConta;

    // Construtor padrão
    public PossuiId() {}

    // Construtor completo
    public PossuiId(String cnpj, int idConta) {
        this.cnpj = cnpj;
        this.idConta = idConta;
    }

    // Getters e Setters
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    // hashCode() e equals() devem ser sobrescritos para garantir a comparação correta entre os objetos PossuiId
}
