package com.fabricaagricola.bdfabrica.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Financeiro")
public class Financeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdFinanceiro")
    private int idFinanceiro;

    @Column(name = "historico_lucro")
    private float historicoLucro;

    @Column(name = "historico_pejuizo")
    private float historicoPrejuizo;

    @Column(name = "dataAtualizacao")
    private LocalDate dataAtualizacao;

    // Construtores
    public Financeiro() {}

    public Financeiro(float historicoLucro, float historicoPrejuizo, LocalDate dataAtualizacao) {
        this.historicoLucro = historicoLucro;
        this.historicoPrejuizo = historicoPrejuizo;
        this.dataAtualizacao = dataAtualizacao;
    }

    // Getters e Setters
    public int getIdFinanceiro() {
        return idFinanceiro;
    }

    public float getHistoricoLucro() {
        return historicoLucro;
    }

    public void setHistoricoLucro(float historicoLucro) {
        this.historicoLucro = historicoLucro;
    }

    public float getHistoricoPrejuizo() {
        return historicoPrejuizo;
    }

    public void setHistoricoPrejuizo(float historicoPrejuizo) {
        this.historicoPrejuizo = historicoPrejuizo;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
