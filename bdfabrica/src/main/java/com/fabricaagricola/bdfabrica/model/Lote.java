package com.fabricaagricola.bdfabrica.model;

import java.time.LocalDate;

public class Lote {
    private String codigo;
    private int idEstoque;
    private int idMateriaPrima;
    private int idProduto;
    private String custo;
    private String descricao;
    private int quantidade;
    private LocalDate dataValidade;

    public Lote() {}

    public Lote(String codigo, int idEstoque, int idMateriaPrima, int idProduto,
                String custo, String descricao, int quantidade, LocalDate dataValidade) {
        this.codigo = codigo;
        this.idEstoque = idEstoque;
        this.idMateriaPrima = idMateriaPrima;
        this.idProduto = idProduto;
        this.custo = custo;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.dataValidade = dataValidade;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(int idEstoque) {
        this.idEstoque = idEstoque;
    }

    public int getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public void setIdMateriaPrima(int idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getCusto() {
        return custo;
    }

    public void setCusto(String custo) {
        this.custo = custo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }
}
