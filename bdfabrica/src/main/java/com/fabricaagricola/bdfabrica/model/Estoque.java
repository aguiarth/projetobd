package com.fabricaagricola.bdfabrica.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fabricaagricola.bdfabrica.enums.TipoMovimentacao;

public class Estoque {
    
    private int idEstoque;
    private TipoMovimentacao tipoMovimentacao;
    private LocalDate dataMovimentacao;
    private LocalTime horaMovimentacao;
    
    public Estoque() {
    	this.dataMovimentacao = LocalDate.now();
    	this.horaMovimentacao = LocalTime.now();
    }

    public Estoque(int idEstoque, TipoMovimentacao tipoMovimentacao, LocalDate dataMovimentacao, LocalTime horaMovimentacao) {
        this.idEstoque = idEstoque;
        this.tipoMovimentacao = tipoMovimentacao;
        this.dataMovimentacao = (dataMovimentacao == null) ? LocalDate.now() : dataMovimentacao;
        this.horaMovimentacao = (horaMovimentacao == null) ? LocalTime.now() : horaMovimentacao;
    }


    public int getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(int idEstoque) {
        this.idEstoque = idEstoque;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public LocalDate getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDate dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public LocalTime getHoraMovimentacao() {
        return horaMovimentacao;
    }

    public void setHoraMovimentacao(LocalTime horaMovimentacao) {
        this.horaMovimentacao = horaMovimentacao;
    }
}
