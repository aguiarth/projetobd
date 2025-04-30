package com.fabricaagricola.bdfabrica.model;

import java.time.LocalDate;

public class MateriaPrima {
    private int idMateriaPrima;
    private String descricao;
    private LocalDate dataValidade;
    private int quantidade;
    private float custoUnitario;
    private float custoTotal;

    public MateriaPrima() {}

    public MateriaPrima(int idMateriaPrima, String descricao, LocalDate dataValidade, int quantidade, float custoUnitario, float custoTotal) {
        this.idMateriaPrima = idMateriaPrima;
        this.descricao = descricao;
        this.dataValidade = dataValidade;
        this.quantidade =  quantidade;
        this.custoUnitario = custoUnitario;
        this.custoTotal = custoTotal;
    }

    public int getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public void setIdMateriaPrima(int idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public float getCustoUnitario() {
        return custoUnitario;
    }

    public void setCustoUnitario(float custoUnitario) {
        this.custoUnitario = custoUnitario;
    }

    public float getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(float custoTotal) {
        this.custoTotal = custoTotal;
    }
}
