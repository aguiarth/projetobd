package entidades;

import java.time.LocalDate;

public abstract class Conta {
	private String idConta; // Read-only fora da classe
	private LocalDate dataEmissao;
	private LocalDate dataVencimento;
	private float valorTotal;
	private StatusConta status;
	
	public Conta(String idConta, LocalDate dataEmissao, LocalDate dataVencimento, float valorTotal,
			StatusConta status) {
		super();
		this.idConta = idConta;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.valorTotal = valorTotal;
		this.status = status;
	}
	
	public String getIdConta() {
	    return idConta;
	}

	public LocalDate getDataEmissao() {
	    return dataEmissao;
	}

	public void setDataEmissao(LocalDate dataEmissao) {
	    this.dataEmissao = dataEmissao;
	}

	public LocalDate getDataVencimento() {
	    return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
	    this.dataVencimento = dataVencimento;
	}

	public float getValorTotal() {
	    return valorTotal;
	}

	public void setValorTotal(float valorTotal) {
	    this.valorTotal = valorTotal;
	}

	public StatusConta getStatus() {
	    return status;
	}

	public void setStatus(StatusConta status) {
	    this.status = status;
	}
	
	public abstract String getTipoConta();
	
	@Override
	public String toString() {
	    return String.format(
	        "Conta [%s]\n" +
	        "Data de Emissão: %s\n" +
	        "Data de Vencimento: %s\n" +
	        "Valor Total: R$ %.2f\n" +
	        "Status: %s",
	        idConta,
	        dataEmissao,
	        dataVencimento,
	        valorTotal,
	        status instanceof StatusConta ? ((StatusConta) status).getDescricao() : status.toString()
	    );
	}
}
