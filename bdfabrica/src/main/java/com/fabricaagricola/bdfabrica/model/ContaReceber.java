package com.fabricaagricola.bdfabrica.model;

import com.fabricaagricola.bdfabrica.enums.StatusConta;
import jakarta.persistence.*;

@Entity
@Table(name = "Receber")
@PrimaryKeyJoinColumn(name = "id_conta") // importante para herança JOINED
public class ContaReceber extends Conta {

    // Construtor padrão
    public ContaReceber() {}

    // Construtor completo
    public ContaReceber(Financeiro financeiro, 
                        java.time.LocalDate dataEmissao, 
                        java.time.LocalDate dataVencimento, 
                        float valorTotal, 
                        StatusConta status) {
        super(financeiro, dataEmissao, dataVencimento, valorTotal, status);
    }

    @Override
    public void processarConta() {
        if (getStatus() == StatusConta.PAGO) {
            Financeiro fin = getFinanceiro();
            float novoLucro = fin.getHistoricoLucro() + getValorTotal();
            fin.setHistoricoLucro(novoLucro);
            fin.setDataAtualizacao(java.time.LocalDate.now());
        }
    }
}
