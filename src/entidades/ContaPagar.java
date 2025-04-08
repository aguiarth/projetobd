package entidades;

import java.time.LocalDate;

public class ContaPagar extends Conta {

    public ContaPagar(String idConta, LocalDate dataEmissao, LocalDate dataVencimento, float valorTotal, StatusConta status) {
        super(idConta, dataEmissao, dataVencimento, valorTotal, status);
    }

    @Override
    public String getTipoConta() {
        return "Conta a Pagar";
    }
    
    
    
    // depois importar o fornecedor e colocar essa substring
    
    /*
    @Override
    public String toString() {
        return super.toString() + "\nFornecedor: " + fornecedor;
    }
    */
}
