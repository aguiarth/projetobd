package entidades;

import java.time.LocalDate;

public class Financeiro {
	private String idFinanceiro; // Read-only fora da classe
	private float lucro;
	private float prejuizo;
	private LocalDate dataAtualizacao;
	
	public Financeiro(String idFinanceiro, float lucro, float prejuizo, LocalDate dataAtualizacao) {
		super();
		this.idFinanceiro = idFinanceiro;
		this.lucro = lucro;
		this.prejuizo = prejuizo;
		this.dataAtualizacao = dataAtualizacao;
	}
	
	public String getId() {
		return idFinanceiro;
	}
	
	public float getLucro() {
		return lucro;
	}
	
	public void setLucro(float lucro) {
		this.lucro = lucro;
	}
	
	public float getPrejuizo() {
		return prejuizo;
	}
	
	public void setPrejuizo(float prejuizo) {
		this.prejuizo = prejuizo;
	}
	
	public LocalDate getDataAtualizacao() {
		return dataAtualizacao;
	}
	public void setDataAtualizacao(LocalDate dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
}
