package com.fabricaagricola.bdfabrica.model;

import java.time.LocalDate;

public class Financeiro {

    private int idFinanceiro;
    private float historicoLucro;
    private float historicoPrejuizo;
    private LocalDate dataAtualizacao;

    public Financeiro() {
        this.dataAtualizacao = LocalDate.now();
    }

    public Financeiro(int idFinanceiro, float historicoLucro, float historicoPrejuizo, LocalDate dataAtualizacao) {
        this.idFinanceiro = idFinanceiro;
        this.historicoLucro = historicoLucro;
        this.historicoPrejuizo = historicoPrejuizo;
        this.dataAtualizacao = (dataAtualizacao == null) ? LocalDate.now() : dataAtualizacao;
    }
    public int getIdFinanceiro() {
        return idFinanceiro;
    }

    public void setIdFinanceiro(int idFinanceiro) {
        this.idFinanceiro = idFinanceiro;
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
        this.dataAtualizacao = (dataAtualizacao == null) ? LocalDate.now() : dataAtualizacao;
    }
}
