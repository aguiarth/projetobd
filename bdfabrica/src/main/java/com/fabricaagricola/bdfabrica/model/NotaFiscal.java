package com.fabricaagricola.bdfabrica.model;

import java.time.LocalDate;

public class NotaFiscal {
	private int chave;
    private float valorImposto;
    private LocalDate dataEmissaoNota;

    public NotaFiscal() {
    	this.dataEmissaoNota = LocalDate.now();
    }

    public NotaFiscal(int chave, float valorImposto, LocalDate dataEmissaoNota) {
    	this.chave = chave;
        this.valorImposto = valorImposto;
        this.dataEmissaoNota = (dataEmissaoNota == null) ? LocalDate.now() : dataEmissaoNota;;
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    public float getValorImposto() {
        return valorImposto;
    }

    public void setValorImposto(float valorImposto) {
        this.valorImposto = valorImposto;
    }

    public LocalDate getDataEmissaoNota() {
        return dataEmissaoNota;
    }

    public void setDataEmissaoNota(LocalDate dataEmissaoNota) {
        this.dataEmissaoNota = dataEmissaoNota;
    }
}
