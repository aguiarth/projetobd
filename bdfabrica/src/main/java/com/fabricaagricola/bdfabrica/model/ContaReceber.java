package com.fabricaagricola.bdfabrica.model;
import java.time.LocalDate;
import com.fabricaagricola.bdfabrica.enums.StatusConta;

public class ContaReceber extends Conta {

	public ContaReceber() {
        super();
    }
	
    public ContaReceber(int idConta) {
        super.setIdConta(idConta);
    }

    public ContaReceber(int idConta, int idFinanceiro, LocalDate dataEmissao, LocalDate dataVencimento, float valorTotal, StatusConta status) {
        super(idConta, idFinanceiro, dataEmissao, dataVencimento, valorTotal, status);
    }
    
}
