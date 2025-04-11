package com.fabricaagricola.bdfabrica;
import jakarta.persistence.*;
import java.util.Date;

import com.fabricaagricola.bdfabrica.model.Financeiro;

@Entity
@Table(name = "Contas")
public class BdfabricaApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdConta")
    private Integer idConta;

    @ManyToOne
    @JoinColumn(name = "IdFinanceiro", referencedColumnName = "IdFinanceiro")
    private Financeiro financeiro;

    @Column(name = "dataEmissao")
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;

    @Column(name = "dataVencimento")
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;

    @Column(name = "valorTotal")
    private Float valorTotal;

    @Column(name = "status")
    private String status;

    // Getters e Setters
    public Integer getIdConta() {
        return idConta;
    }

    public void setIdConta(Integer idConta) {
        this.idConta = idConta;
    }

    public Financeiro getFinanceiro() {
        return financeiro;
    }

    public void setFinanceiro(Financeiro financeiro) {
        this.financeiro = financeiro;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
