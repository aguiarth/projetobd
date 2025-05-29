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

    // DTOs

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

    // Métodos usando Procedures

    public DashboardResumoDTO getResumoGeralPedidos() {
        String sql = "{CALL dashboard_GetResumoGeralPedidos()}";
        return jdbc.query(sql, rs -> {
            if (rs.next()) {
                return new DashboardResumoDTO(
                    rs.getInt("total_pedidos"),
                    rs.getInt("pedidos_abertos"),
                    rs.getInt("pedidos_finalizados"),
                    Optional.ofNullable(rs.getBigDecimal("receita_mensal_pedidos")).orElse(BigDecimal.ZERO)
                );
            }
            return null;
        });
    }

    public int getTotalClientes() {
        String sql = "{CALL dashboard_GetTotalClientes()}";
        return jdbc.query(sql, rs -> rs.next() ? rs.getInt("total_clientes") : 0);
    }

    public int getTotalLotesRegistrados() {
        String sql = "{CALL dashboard_GetTotalLotesRegistrados()}";
        return jdbc.query(sql, rs -> rs.next() ? rs.getInt("total_lotes_registrados") : 0);
    }

    public BigDecimal getValorTotalCustoLotes() {
        String sql = "{CALL dashboard_GetValorTotalCustoLotes()}";
        return jdbc.query(sql, rs -> rs.next() ? rs.getBigDecimal("valor_total_custo_lotes") : BigDecimal.ZERO);
    }

    public ContasResumoDTO getResumoContas() {
        String sql = "{CALL dashboard_GetResumoContas()}";
        return jdbc.query(sql, rs -> {
            if (rs.next()) {
                return new ContasResumoDTO(
                    rs.getBigDecimal("total_contas_a_pagar_pendente"),
                    rs.getBigDecimal("total_contas_a_receber_pendente"),
                    rs.getBigDecimal("total_pago_mes"),
                    rs.getBigDecimal("total_recebido_mes")
                );
            }
            return null;
        });
    }

    public List<LucroMensalDTO> getLucroMensalHistorico(int ultimosMeses) {
        String sql = "{CALL dashboard_GetLucroMensalHistorico(?)}";
        return jdbc.query(sql, ps -> ps.setInt(1, ultimosMeses), (rs, rowNum) -> {
            BigDecimal receita = Optional.ofNullable(rs.getBigDecimal("receita_paga")).orElse(BigDecimal.ZERO);
            BigDecimal despesa = Optional.ofNullable(rs.getBigDecimal("despesa_paga")).orElse(BigDecimal.ZERO);
            return new LucroMensalDTO(
                rs.getString("mes_ano"),
                receita,
                despesa
            );
        });
    }
}
