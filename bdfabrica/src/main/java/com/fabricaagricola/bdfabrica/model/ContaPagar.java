package com.fabricaagricola.bdfabrica.model;

import com.fabricaagricola.bdfabrica.enums.StatusConta;

import jakarta.persistence.*;

@Entity
@Table(name = "Pagar")
public class ContaPagar extends Conta {

    @ManyToOne
    @JoinColumn(name = "cnpj", referencedColumnName = "cnpj")
    private Fornecedor fornecedor;

    // Construtor padrão
    public ContaPagar() {}

    // Construtor completo
    public ContaPagar(Financeiro financeiro,
                      java.time.LocalDate dataEmissao,
                      java.time.LocalDate dataVencimento,
                      float valorTotal,
                      StatusConta status,
                      Fornecedor fornecedor) {
        super(financeiro, dataEmissao, dataVencimento, valorTotal, status);
        this.fornecedor = fornecedor;
    }

    // Getter e Setter
    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public void processarConta() {
        // lógica de processamento específica para contas a pagar
        System.out.println("Processando conta a pagar: " + getIdConta());
    }
}
