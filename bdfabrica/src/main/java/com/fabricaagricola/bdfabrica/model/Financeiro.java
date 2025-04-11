package com.fabricaagricola.bdfabrica.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Financeiro")
public class Financeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_financeiro")
    private int idFinanceiro;

    @Column(name = "historico_lucro", nullable = false)
    private float historicoLucro;

    @Column(name = "historico_prejuizo", nullable = false)
    private float historicoPrejuizo;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDate dataAtualizacao;

    // Construtor padrão
    public Financeiro() {}

    // Construtor completo
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
