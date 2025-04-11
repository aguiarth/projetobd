package com.fabricaagricola.bdfabrica.model;

import com.fabricaagricola.bdfabrica.enums.StatusConta;
import jakarta.persistence.*;

@Entity
@Table(name = "Pagar")
@PrimaryKeyJoinColumn(name = "id_conta") // conecta a PK da superclasse "Conta"
public class ContaPagar extends Conta {

    @ManyToOne
    @JoinColumn(name = "cnpj", referencedColumnName = "cnpj", nullable = false)
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
        System.out.println("Processando conta a pagar: " + getIdConta());
    }
}
