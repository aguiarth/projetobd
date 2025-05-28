package com.fabricaagricola.bdfabrica.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DashboardRepository {

    private final JdbcTemplate jdbc;

    public DashboardRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    // Pedidos
    public DashboardResumoDTO getResumoGeralPedidos() {
        String sql = """
            SELECT
              (SELECT COUNT(*) FROM Pedido) AS total_pedidos,
              (SELECT COUNT(*) FROM Pedido WHERE status = 'ABERTO') AS pedidos_abertos,
              (SELECT COUNT(*) FROM Pedido WHERE status = 'FINALIZADO') AS pedidos_finalizados,
              -- Assumindo que 'receita_mensal' é a soma de valor_total dos pedidos emitidos no mês atual
              (SELECT SUM(valor_total) FROM Pedido WHERE MONTH(data_emissao) = MONTH(CURRENT_DATE()) AND YEAR(data_emissao) = YEAR(CURRENT_DATE())) AS receita_mensal_pedidos
        """;

        return jdbc.queryForObject(sql, (rs, rowNum) -> new DashboardResumoDTO(
            rs.getInt("total_pedidos"),
            rs.getInt("pedidos_abertos"),
            rs.getInt("pedidos_finalizados"),
            Optional.ofNullable(rs.getBigDecimal("receita_mensal_pedidos")).orElse(BigDecimal.ZERO)
        ));
    }
    
    public class DashboardResumoDTO {
        private int totalPedidos;
        private int pedidosAbertos;
        private int pedidosFinalizados;
        private BigDecimal receitaMensalPedidos;

        public DashboardResumoDTO(int totalPedidos, int pedidosAbertos, int pedidosFinalizados, BigDecimal receitaMensalPedidos) {
            this.totalPedidos = totalPedidos;
            this.pedidosAbertos = pedidosAbertos;
            this.pedidosFinalizados = pedidosFinalizados;
            this.receitaMensalPedidos = receitaMensalPedidos;
        }


        public int getTotalPedidos() { return totalPedidos; }
        public int getPedidosAbertos() { return pedidosAbertos; }
        public int getPedidosFinalizados() { return pedidosFinalizados; }
        public BigDecimal getReceitaMensalPedidos() { return receitaMensalPedidos; } 
    }
    
    
    // Total de clientes
    public int getTotalClientes() {
        String sql = "SELECT COUNT(*) FROM Cliente";
        return Optional.ofNullable(jdbc.queryForObject(sql, Integer.class)).orElse(0);
    }
    
    // total de lotes registrados
    public int getTotalLotesRegistrados() {
        String sql = "SELECT COUNT(*) FROM Lote";
        return Optional.ofNullable(jdbc.queryForObject(sql, Integer.class)).orElse(0);
    }
    
    // valor total dos custos 
    public BigDecimal getValorTotalCustoLotes() {
        String sql = """
            SELECT SUM(CAST(custo AS DECIMAL(10, 2))) FROM Lote
        """;
        return Optional.ofNullable(jdbc.queryForObject(sql, BigDecimal.class)).orElse(BigDecimal.ZERO);
    }
    
    
    // lucro e gastos das contas
    public class ContasResumoDTO {
        private BigDecimal totalContasAPagarPendente;
        private BigDecimal totalContasAReceberPendente;
        private BigDecimal totalPagoMes;
        private BigDecimal totalRecebidoMes;

        public ContasResumoDTO(BigDecimal totalContasAPagarPendente, BigDecimal totalContasAReceberPendente, BigDecimal totalPagoMes, BigDecimal totalRecebidoMes) {
            this.totalContasAPagarPendente = totalContasAPagarPendente;
            this.totalContasAReceberPendente = totalContasAReceberPendente;
            this.totalPagoMes = totalPagoMes;
            this.totalRecebidoMes = totalRecebidoMes;
        }

        public BigDecimal getTotalContasAPagarPendente() { return totalContasAPagarPendente; }
        public BigDecimal getTotalContasAReceberPendente() { return totalContasAReceberPendente; }
        public BigDecimal getTotalPagoMes() { return totalPagoMes; }
        public BigDecimal getTotalRecebidoMes() { return totalRecebidoMes; }
    }
    
    
    public ContasResumoDTO getResumoContas() {
        String sqlPagarPendente = "SELECT SUM(valor_total) FROM Conta cp JOIN ContaPagar cpp ON cp.id_conta = cpp.id_conta WHERE cp.status = 'PENDENTE'";
        String sqlReceberPendente = "SELECT SUM(valor_total) FROM Conta cr JOIN ContaReceber crr ON cr.id_conta = crr.id_conta WHERE cr.status = 'PENDENTE'";
        
        String sqlTotalPagoMes = """
            SELECT SUM(valor_total)
            FROM Conta c
            JOIN ContaPagar cp ON c.id_conta = cp.id_conta
            WHERE c.status = 'PAGO' AND MONTH(c.data_emissao) = MONTH(CURRENT_DATE()) AND YEAR(c.data_emissao) = YEAR(CURRENT_DATE())
        """;
        String sqlTotalRecebidoMes = """
            SELECT SUM(valor_total)
            FROM Conta c
            JOIN ContaReceber cr ON c.id_conta = cr.id_conta
            WHERE c.status = 'PAGO' AND MONTH(c.data_emissao) = MONTH(CURRENT_DATE()) AND YEAR(c.data_emissao) = YEAR(CURRENT_DATE())
        """;

        BigDecimal totalPagarPendente = Optional.ofNullable(jdbc.queryForObject(sqlPagarPendente, BigDecimal.class)).orElse(BigDecimal.ZERO);
        BigDecimal totalReceberPendente = Optional.ofNullable(jdbc.queryForObject(sqlReceberPendente, BigDecimal.class)).orElse(BigDecimal.ZERO);
        BigDecimal totalPagoMes = Optional.ofNullable(jdbc.queryForObject(sqlTotalPagoMes, BigDecimal.class)).orElse(BigDecimal.ZERO);
        BigDecimal totalRecebidoMes = Optional.ofNullable(jdbc.queryForObject(sqlTotalRecebidoMes, BigDecimal.class)).orElse(BigDecimal.ZERO);

        return new ContasResumoDTO(totalPagarPendente, totalReceberPendente, totalPagoMes, totalRecebidoMes);
    }

    
    
    //lucro histórico mensal
    public class LucroMensalDTO {
        private String mesAno;
        private BigDecimal receita;
        private BigDecimal despesa;
        private BigDecimal lucro;

        public LucroMensalDTO(String mesAno, BigDecimal receita, BigDecimal despesa) {
            this.mesAno = mesAno;
            this.receita = receita;
            this.despesa = despesa;
            this.lucro = receita.subtract(despesa);
        }

        public String getMesAno() { return mesAno; }
        public BigDecimal getReceita() { return receita; }
        public BigDecimal getDespesa() { return despesa; }
        public BigDecimal getLucro() { return lucro; }
    }

    
    public List<LucroMensalDTO> getLucroMensalHistorico(int ultimosMeses) {
    	String sql = """
                SELECT
                    DATE_FORMAT(c.data_emissao, '%Y-%m') AS mes_ano,
                    SUM(CASE WHEN EXISTS (SELECT 1 FROM ContaReceber cr WHERE cr.id_conta = c.id_conta) AND c.status = 'PAGO' THEN c.valor_total ELSE 0 END) AS receita_paga,
                    SUM(CASE WHEN EXISTS (SELECT 1 FROM ContaPagar cp WHERE cp.id_conta = c.id_conta) AND c.status = 'PAGO' THEN c.valor_total ELSE 0 END) AS despesa_paga
                FROM Conta c
                WHERE c.data_emissao >= DATE_SUB(CURRENT_DATE(), INTERVAL ? MONTH)
                GROUP BY mes_ano
                ORDER BY mes_ano ASC;
            """;

    	return jdbc.query(sql, (rs, rowNum) -> {
            BigDecimal receita = Optional.ofNullable(rs.getBigDecimal("receita_paga")).orElse(BigDecimal.ZERO);
            BigDecimal despesa = Optional.ofNullable(rs.getBigDecimal("despesa_paga")).orElse(BigDecimal.ZERO);
            return new LucroMensalDTO(
                rs.getString("mes_ano"),
                receita,
                despesa
            );
        }, ultimosMeses);
    }
}

