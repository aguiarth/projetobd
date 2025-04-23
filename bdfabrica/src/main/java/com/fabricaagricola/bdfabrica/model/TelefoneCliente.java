package com.fabricaagricola.bdfabrica.model;

public class TelefoneCliente {
	private String cnpj;
    private String telefoneCliente;

    public TelefoneCliente() {}

    public TelefoneCliente(String cnpj, String telefoneCliente) {
        this.cnpj = cnpj;
        this.telefoneCliente = telefoneCliente;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    } 
}
