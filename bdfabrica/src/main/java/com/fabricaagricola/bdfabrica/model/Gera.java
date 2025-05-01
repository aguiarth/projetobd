package com.fabricaagricola.bdfabrica.model;

public class Gera {
    private int idOrdem;
    private int idProduto;
    
    // perguntar a laura se tem problema esse aributo estar aq sendo q ele não existe no script nem no lógico
    private ProdutoAcabado produtoAcabado;
    
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
    
    public ProdutoAcabado getProdutoAcabado() {
        return produtoAcabado;
    }

    public void setProdutoAcabado(ProdutoAcabado produtoAcabado) {
        this.produtoAcabado = produtoAcabado;
    }
}
