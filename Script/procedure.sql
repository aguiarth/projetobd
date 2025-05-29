-- getResumoGeralPedidos
CREATE DEFINER = root@localhost PROCEDURE dashboard_GetResumoGeralPedidos()
BEGIN
    SELECT
        (SELECT COUNT(*) FROM Pedido) AS total_pedidos,
        (SELECT COUNT(*) FROM Pedido WHERE status = 'ABERTO') AS pedidos_abertos,
        (SELECT COUNT(*) FROM Pedido WHERE status = 'FINALIZADO') AS pedidos_finalizados,
        COALESCE(SUM(valor_total), 0.00) AS receita_mensal_pedidos
    FROM Pedido
    WHERE MONTH(data_emissao) = MONTH(CURRENT_DATE())
      AND YEAR(data_emissao) = YEAR(CURRENT_DATE());
END;

-- getTotalClientes
CREATE DEFINER = root@localhost PROCEDURE dashboard_GetTotalClientes()
BEGIN
    SELECT COUNT(*) AS total_clientes FROM Cliente;
END;


-- getTotalLotesRegistrados
CREATE DEFINER = root@localhost PROCEDURE dashboard_GetTotalLotesRegistrados()
BEGIN
    SELECT COUNT(*) AS total_lotes_registrados FROM Lote;
END;

-- getValorTotalCustoLotes
CREATE DEFINER = root@localhost PROCEDURE dashboard_GetValorTotalCustoLotes()
BEGIN
    SELECT COALESCE(SUM(CAST(REPLACE(custo, ',', '.') AS DECIMAL(10, 2))), 0.00) AS valor_total_custo_lotes
    FROM Lote;
END;

-- getResumoContas
CREATE DEFINER = root@localhost PROCEDURE dashboard_GetResumoContas()
BEGIN
    SELECT
        COALESCE(SUM(CASE WHEN cp.id_conta IS NOT NULL AND c.status = 'PENDENTE' THEN c.valor_total ELSE 0 END), 0.00) AS total_contas_a_pagar_pendente,
        COALESCE(SUM(CASE WHEN cr.id_conta IS NOT NULL AND c.status = 'PENDENTE' THEN c.valor_total ELSE 0 END), 0.00) AS total_contas_a_receber_pendente,
        COALESCE(SUM(CASE WHEN cp.id_conta IS NOT NULL AND c.status = 'PAGO' AND MONTH(c.data_emissao) = MONTH(CURRENT_DATE()) AND YEAR(c.data_emissao) = YEAR(CURRENT_DATE()) THEN c.valor_total ELSE 0 END), 0.00) AS total_pago_mes,
        COALESCE(SUM(CASE WHEN cr.id_conta IS NOT NULL AND c.status = 'PAGO' AND MONTH(c.data_emissao) = MONTH(CURRENT_DATE()) AND YEAR(c.data_emissao) = YEAR(CURRENT_DATE()) THEN c.valor_total ELSE 0 END), 0.00) AS total_recebido_mes
    FROM Conta c
    LEFT JOIN ContaPagar cp ON c.id_conta = cp.id_conta
    LEFT JOIN ContaReceber cr ON c.id_conta = cr.id_conta;
END;

-- getLucroMensalHistorico
CREATE DEFINER = root@localhost PROCEDURE dashboard_GetLucroMensalHistorico(IN ultimosMeses INT)
BEGIN
    SELECT
        DATE_FORMAT(c.data_emissao, '%Y-%m') AS mes_ano,
        COALESCE(SUM(CASE WHEN EXISTS (SELECT 1 FROM ContaReceber cr WHERE cr.id_conta = c.id_conta) AND c.status = 'PAGO' THEN c.valor_total ELSE 0 END), 0.00) AS receita_paga,
        COALESCE(SUM(CASE WHEN EXISTS (SELECT 1 FROM ContaPagar cp WHERE cp.id_conta = c.id_conta) AND c.status = 'PAGO' THEN c.valor_total ELSE 0 END), 0.00) AS despesa_paga
    FROM Conta c
    WHERE c.data_emissao >= DATE_SUB(CURRENT_DATE(), INTERVAL ultimosMeses MONTH)
    GROUP BY mes_ano
    ORDER BY mes_ano ASC;
END;
