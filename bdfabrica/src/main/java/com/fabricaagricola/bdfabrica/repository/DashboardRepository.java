package com.fabricaagricola.bdfabrica.repository;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardRepository {

    private final JdbcTemplate jdbc;

    public DashboardRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public DashboardResumoDTO getResumoGeral() {
        String sql = """
            SELECT 
              (SELECT COUNT(*) FROM Pedido) AS total_pedidos,
              (SELECT COUNT(*) FROM Pedido WHERE status = 'ABERTO') AS pedidos_abertos,
              (SELECT COUNT(*) FROM Pedido WHERE status = 'FINALIZADO') AS pedidos_finalizados,
              (SELECT SUM(valor_total) FROM Pedido WHERE MONTH(data_emissao) = MONTH(CURRENT_DATE)) AS receita_mensal
        """;

        return jdbc.queryForObject(sql, (rs, rowNum) -> new DashboardResumoDTO(
            rs.getInt("total_pedidos"),
            rs.getInt("pedidos_abertos"),
            rs.getInt("pedidos_finalizados"),
            rs.getBigDecimal("receita_mensal")
        ));
    }
    
    public class DashboardResumoDTO {
        private int totalPedidos;
        private int pedidosAbertos;
        private int pedidosFinalizados;
        private BigDecimal receitaMensal;

        public DashboardResumoDTO(int totalPedidos, int pedidosAbertos, int pedidosFinalizados, BigDecimal receitaMensal) {
            this.totalPedidos = totalPedidos;
            this.pedidosAbertos = pedidosAbertos;
            this.pedidosFinalizados = pedidosFinalizados;
            this.receitaMensal = receitaMensal;
        }

        // Getters
        public int getTotalPedidos() { return totalPedidos; }
        public int getPedidosAbertos() { return pedidosAbertos; }
        public int getPedidosFinalizados() { return pedidosFinalizados; }
        public BigDecimal getReceitaMensal() { return receitaMensal; }
    }

}

