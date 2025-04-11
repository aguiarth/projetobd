package com.fabricaagricola.bdfabrica.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "Pagar")
public class ContaPagar extends Conta {

    @ManyToOne
    @JoinColumn(name = "cnpj")
    private Fornecedor fornecedor;

    public ContaPagar() {}

    public ContaPagar(int idConta, LocalDate dataEmissao, LocalDate dataVencimento,
                      float valorTotal, StatusConta status, Fornecedor fornecedor) {
        super(idConta, dataEmissao, dataVencimento, valorTotal, status);
        this.fornecedor = fornecedor;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public void processarConta() {
        if (getStatus() == StatusConta.PENDENTE) {
            setStatus(StatusConta.PAGO);
        }
    }
}
