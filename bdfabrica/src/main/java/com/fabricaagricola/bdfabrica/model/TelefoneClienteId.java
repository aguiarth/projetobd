package com.fabricaagricola.bdfabrica.model;

import java.io.Serializable;
import java.util.Objects;

public class TelefoneClienteId implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cliente; // corresponde ao campo cliente.cnpj
    private String telefone;

    public TelefoneClienteId() {}

    public TelefoneClienteId(String cliente, String telefone) {
        this.cliente = cliente;
        this.telefone = telefone;
    }

    // equals e hashCode são obrigatórios
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TelefoneClienteId)) return false;
        TelefoneClienteId that = (TelefoneClienteId) o;
        return Objects.equals(cliente, that.cliente) && Objects.equals(telefone, that.telefone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, telefone);
    }

    // getters e setters
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
