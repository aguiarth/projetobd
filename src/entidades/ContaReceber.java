package entidades;

import java.time.LocalDate;

public class ContaReceber extends Conta {

    public ContaReceber(String idConta, LocalDate dataEmissao, LocalDate dataVencimento, float valorTotal, StatusConta status) {
        super(idConta, dataEmissao, dataVencimento, valorTotal, status);
    }

    @Override
    public String getTipoConta() {
        return "Conta a Receber";
    }
    
    // depois importar o cliente e colocar essa substring
    
    /*
    @Override
    public String toString() {
        return super.toString() + "\nCliente: " + cliente;
    }
    */
}
	