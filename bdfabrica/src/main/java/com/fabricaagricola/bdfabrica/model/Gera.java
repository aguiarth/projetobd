package com.fabricaagricola.bdfabrica.model;

public class Gera {
    private int idOrdem;
    private int idProduto;

    public Gera() {}

    public Gera(int idOrdem, int idProduto) {
        this.idOrdem = idOrdem;
        this.idProduto = idProduto;
    }

    public int getIdOrdem() {
        return idOrdem;
    }

    public void setIdOrdem(int idOrdem) {
        this.idOrdem = idOrdem;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }
}
