package com.fabricaagricola.bdfabrica.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "NotaFiscal")
public class NotaFiscal {

    @Id
    @Column(length = 45)
    private String chave;

    @Column
    private float valorImposto;

    @Column
    private LocalDate dataEmissaoNota;

    // Construtor padrão
    public NotaFiscal() {}

    // Construtor completo
    public NotaFiscal(String chave, float valorImposto, LocalDate dataEmissaoNota) {
        this.chave = chave;
        this.valorImposto = valorImposto;
        this.dataEmissaoNota = dataEmissaoNota;
    }

    // Getters e Setters
    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
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
