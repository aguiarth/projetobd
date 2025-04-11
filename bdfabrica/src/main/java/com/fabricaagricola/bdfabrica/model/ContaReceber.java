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
        // lógica de processamento específica para contas a receber
        System.out.println("Processando conta a receber: " + getIdConta());
    }
}
