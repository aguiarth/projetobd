USE bd_fabrica_descartaveis;

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Inserir dados em Financeiro
INSERT INTO Financeiro (historico_lucro, historico_prejuizo, data_atualizacao) VALUES
(1500.00, 800.00, '2024-01-31'),
(2000.00, 1200.00, '2024-02-29'),
(1800.00, 900.00, '2024-03-31'),
(2500.00, 1500.00, '2024-04-30'),
(3000.00, 1800.00, '2024-05-31'),
(2200.00, 1100.00, '2024-06-30'),
(2800.00, 1600.00, '2024-07-31'),
(3500.00, 2000.00, '2024-08-31'),
(2700.00, 1400.00, '2024-09-30'),
(3200.00, 1900.00, '2024-10-31'),
(2900.00, 1700.00, '2024-11-30'),
(3800.00, 2200.00, '2024-12-31'),
(1000.00, 500.00, '2025-01-31'), -- Mês atual ou recente
(1500.00, 700.00, '2025-02-28'),
(2000.00, 900.00, '2025-03-31'),
(2500.00, 1200.00, '2025-04-30'),
(1800.00, 800.00, '2025-05-28'); -- Mês atual

-- 2. Inserir dados em Cliente
INSERT INTO Cliente (cnpj, razao_social, rua, numero, cidade, cep, email) VALUES
('11.111.111/0001-11', 'Cliente A Descartáveis Ltda.', 'Rua das Flores', '100', 'São Paulo', '01000-000', 'contato@clienteA.com'),
('22.222.222/0001-22', 'Distribuidora B Plásticos S.A.', 'Av. Principal', '250', 'Rio de Janeiro', '20000-000', 'vendas@distribuidoraB.com'),
('33.333.333/0001-33', 'Comércio C Embalagens', 'Travessa da Paz', '15', 'Belo Horizonte', '30000-000', 'comercial@comercioC.com'),
('44.444.444/0001-44', 'Mercado D Produtos', 'Rua do Comércio', '300', 'Porto Alegre', '90000-000', 'gerencia@mercadoD.com');

-- 3. Inserir dados em Telefone_cliente
INSERT INTO Telefone_cliente (cnpj, telefone_cliente) VALUES
('11.111.111/0001-11', '(11) 98765-4321'),
('11.111.111/0001-11', '(11) 3333-4444'),
('22.222.222/0001-22', '(21) 99887-6655'),
('33.333.333/0001-33', '(31) 97766-5544');

-- 4. Inserir dados em Fornecedor
INSERT INTO Fornecedor (cnpj, razao_social, endereco, telefone, condicoes_pagamento) VALUES
('55.555.555/0001-55', 'Fornecedor X Resinas', 'Av. Industrial, 500', '(11) 5555-1111', 'CARTAO_CREDITO'),
('66.666.666/0001-66', 'Papelaria Y Materiais', 'Rua da Floresta, 20', '(21) 6666-2222', 'PIX'),
('77.777.777/0001-77', 'Química Z Componentes', 'Praça Central, 10', '(31) 7777-3333', 'BOLETO');

-- 5. Inserir dados em ProdutoAcabado
INSERT INTO ProdutoAcabado (descricao, data_finalizacao) VALUES
('Copo Descartável 200ml', '2025-05-20'),
('Prato Descartável Grande', '2025-05-22'),
('Talher Descartável Kit', '2025-05-25'),
('Bandeja de Isopor Média', '2025-05-26'),
('Guardanapo de Papel Pct', '2025-05-27');

-- 6. Inserir dados em MateriaPrima (triggers devem calcular custo_total)
INSERT INTO MateriaPrima (descricao, data_validade, quantidade, custo_unitario) VALUES
('Resina PP', '2026-12-31', 5000, 0.85),
('Celulose Branca', '2027-01-15', 3000, 1.20),
('Pigmento Azul', '2025-08-01', 100, 15.00),
('Plástico PET', '2026-10-20', 4000, 0.90);

-- 7. Inserir dados em Estoque
INSERT INTO Estoque (tipo_movimentacao) VALUES
('ENTRADA'),
('SAIDA'), -- Lembre-se da correção: se o seu CHECK é 'SAÍDA' (com acento), mude para 'SAÍDA' aqui.
('ENTRADA'),
('SAIDA'); -- E aqui também.

-- 8. Inserir dados em Lote (referencia Estoque, MateriaPrima, ProdutoAcabado)
INSERT INTO Lote (codigo, id_estoque, id_materia_prima, id_produto, custo, descricao, quantidade, data_validade) VALUES
('LOTE-RESINA-001', 1, 1, 1, '4250.00', 'Lote de Resina PP para copos', 5000, '2026-12-31'),
('LOTE-CELULOSE-002', 3, 2, 5, '3600.00', 'Lote de Celulose para guardanapos', 3000, '2027-01-15'),
('LOTE-PIGMENTO-003', 1, 3, 1, '1500.00', 'Lote de Pigmento Azul', 100, '2025-08-01'),
('LOTE-PET-004', 3, 4, 2, '3600.00', 'Lote de Plástico PET para pratos', 4000, '2026-10-20');

-- 9. Inserir dados em Vincula (Fornecedor e Lote)
INSERT INTO Vincula (cnpj, codigo) VALUES
('55.555.555/0001-55', 'LOTE-RESINA-001'),
('66.666.666/0001-66', 'LOTE-CELULOSE-002'),
('55.555.555/0001-55', 'LOTE-PIGMENTO-003'),
('55.555.555/0001-55', 'LOTE-PET-004');

-- 10. Inserir dados em Pedido (com datas variadas para histórico)
INSERT INTO Pedido (numero, data_emissao, valor_total, status, forma_pagamento) VALUES
('PED-001/2024', '2024-01-10', 1500.00, 'FINALIZADO', 'CARTAO_CREDITO'),
('PED-002/2024', '2024-02-15', 2200.00, 'FINALIZADO', 'BOLETO'),
('PED-003/2024', '2024-03-20', 800.00, 'CANCELADO', 'BOLETO'),
('PED-004/2024', '2024-04-05', 3000.00, 'FINALIZADO', 'CARTAO_DEBITO'),
('PED-005/2024', '2024-05-10', 1200.00, 'ABERTO', 'CARTAO_CREDITO'),
('PED-006/2024', '2024-05-20', 950.00, 'ABERTO', 'PIX'),
('PED-007/2025', '2025-01-05', 1800.00, 'FINALIZADO', 'BOLETO'),
('PED-008/2025', '2025-02-10', 2500.00, 'FINALIZADO', 'CARTAO_CREDITO'),
('PED-009/2025', '2025-03-15', 1100.00, 'ABERTO', 'BOLETO'),
('PED-010/2025', '2025-04-20', 3200.00, 'FINALIZADO', 'CARTAO_DEBITO'),
('PED-011/2025', '2025-05-01', 750.00, 'ABERTO', 'CARTAO_CREDITO'), -- Pedido do mês atual
('PED-012/2025', '2025-05-15', 1500.00, 'ABERTO', 'BOLETO'); -- Pedido do mês atual

-- 11. Inserir dados em Realiza (Cliente e Pedido)
INSERT INTO Realiza (cnpj, numero) VALUES
('11.111.111/0001-11', 'PED-001/2024'),
('22.222.222/0001-22', 'PED-002/2024'),
('11.111.111/0001-11', 'PED-003/2024'),
('33.333.333/0001-33', 'PED-004/2024'),
('44.444.444/0001-44', 'PED-005/2024'),
('11.111.111/0001-11', 'PED-006/2024'),
('22.222.222/0001-22', 'PED-007/2025'),
('33.333.333/0001-33', 'PED-008/2025'),
('11.111.111/0001-11', 'PED-009/2025'),
('44.444.444/0001-44', 'PED-010/2025'),
('22.222.222/0001-22', 'PED-011/2025'),
('33.333.333/0001-33', 'PED-012/2025');

-- 12. Inserir dados em Contem (Pedido e ProdutoAcabado)
INSERT INTO Contem (numero, id_produto) VALUES
('PED-001/2024', 1),
('PED-001/2024', 3),
('PED-002/2024', 2),
('PED-002/2024', 4),
('PED-003/2024', 1),
('PED-004/2024', 5),
('PED-004/2024', 1),
('PED-005/2024', 3),
('PED-006/2024', 2),
('PED-007/2025', 1),
('PED-007/2025', 5),
('PED-008/2025', 2),
('PED-008/2025', 3),
('PED-009/2025', 4),
('PED-010/2025', 1),
('PED-010/2025', 2),
('PED-011/2025', 3),
('PED-012/2025', 4);

-- 13. Inserir dados em Expedicao
INSERT INTO Expedicao (data_expedicao, hora_expedicao, status) VALUES
('2024-01-12', '10:00:00', 'ENTREGUE'),
('2024-02-17', '11:30:00', 'ENTREGUE'),
('2024-04-07', '09:45:00', 'ENTREGUE'),
('2025-01-07', '13:00:00', 'ENTREGUE'),
('2025-02-12', '14:00:00', 'ENTREGUE'),
('2025-04-22', '10:30:00', 'ENTREGUE'),
('2025-05-03', '11:00:00', 'PENDENTE'), -- Expedição pendente
('2025-05-17', '15:00:00', 'PENDENTE'); -- Expedição pendente

-- 14. Inserir dados em Envia (Pedido e Expedicao)
INSERT INTO Envia (numero, id_expedicao) VALUES
('PED-001/2024', 1),
('PED-002/2024', 2),
('PED-004/2024', 3),
('PED-007/2025', 4),
('PED-008/2025', 5),
('PED-010/2025', 6),
('PED-011/2025', 7),
('PED-012/2025', 8);

-- 15. Inserir dados em EntregaTransporte
INSERT INTO EntregaTransporte (numero_entrega, id_expedicao, data_prevista, data_saida, data_entrega) VALUES
('ENT-001', 1, '2024-01-15', '2024-01-12', '2024-01-14'),
('ENT-002', 2, '2024-02-20', '2024-02-17', '2024-02-19'),
('ENT-003', 3, '2024-04-10', '2024-04-07', '2024-04-09'),
('ENT-004', 4, '2025-01-08', '2025-01-05', '2025-01-07'),
('ENT-005', 5, '2025-02-13', '2025-02-10', '2025-02-12'),
('ENT-006', 6, '2025-04-25', '2025-04-22', '2025-04-24'),
('ENT-007', 7, '2025-05-06', '2025-05-03', NULL), -- Em trânsito
('ENT-008', 8, '2025-05-20', '2025-05-17', NULL); -- Em trânsito

-- 16. Inserir dados em OrdemProducao
INSERT INTO OrdemProducao (produto_fabricado, quantidade_produto, data_inicio, data_final, descricao) VALUES
('Copo Descartável 200ml', 10000, '2025-05-01', '2025-05-10', 'Produção de copos padrão'),
('Prato Descartável Grande', 5000, '2025-05-05', '2025-05-15', 'Produção de pratos grandes'),
('Talher Descartável Kit', 7000, '2025-05-10', '2025-05-20', 'Produção de kits de talheres'),
('Bandeja de Isopor Média', 3000, '2025-05-12', '2025-05-22', 'Produção de bandejas');

-- 17. Inserir dados em Gera (OrdemProducao e ProdutoAcabado)
INSERT INTO Gera (id_ordem, id_produto) VALUES
(1, 1), -- Copo Descartável 200ml
(2, 2), -- Prato Descartável Grande
(3, 3), -- Talher Descartável Kit
(4, 4); -- Bandeja de Isopor Média

-- 18. Inserir dados em Consome (MateriaPrima e OrdemProducao)
INSERT INTO Consome (id_materia_prima, id_ordem) VALUES
(1, 1), -- Resina PP para Copo
(3, 1), -- Pigmento Azul para Copo
(4, 2), -- Plástico PET para Prato
(2, 3); -- Celulose para Talher (assumindo uso em embalagem ou similar)

-- 19. Inserir dados em Conta (referencia Financeiro)
INSERT INTO Conta (id_financeiro, data_emissao, data_vencimento, valor_total, status) VALUES
(13, '2025-01-01', '2025-01-10', 500.00, 'PAGO'), -- Conta a Pagar (Janeiro)
(13, '2025-01-05', '2025-01-15', 1000.00, 'PAGO'), -- Conta a Receber (Janeiro)
(14, '2025-02-01', '2025-02-10', 700.00, 'PAGO'), -- Conta a Pagar (Fevereiro)
(14, '2025-02-05', '2025-02-15', 1500.00, 'PAGO'), -- Conta a Receber (Fevereiro)
(15, '2025-03-01', '2025-03-10', 900.00, 'PAGO'), -- Conta a Pagar (Março)
(15, '2025-03-05', '2025-03-15', 2000.00, 'PAGO'), -- Conta a Receber (Março)
(16, '2025-04-01', '2025-04-10', 1200.00, 'PAGO'), -- Conta a Pagar (Abril)
(16, '2025-04-05', '2025-04-15', 2500.00, 'PAGO'), -- Conta a Receber (Abril)
(17, '2025-05-01', '2025-05-10', 800.00, 'PENDENTE'), -- Conta a Pagar (Maio - PENDENTE)
(17, '2025-05-05', '2025-05-15', 1800.00, 'PENDENTE'), -- Conta a Receber (Maio - PENDENTE)
(17, '2025-05-10', '2025-05-20', 500.00, 'PAGO'), -- Conta a Pagar (Maio - PAGO)
(17, '2025-05-12', '2025-05-22', 1000.00, 'PAGO'), -- Conta a Receber (Maio - PAGO)
(17, '2025-04-20', '2025-05-27', 300.00, 'VENCIDO'); -- Conta vencida

-- 20. Inserir dados em ContaReceber
INSERT INTO ContaReceber (id_conta) VALUES
(2), (4), (6), (8), (10), (12);

-- 21. Inserir dados em ContaPagar
INSERT INTO ContaPagar (id_conta, cnpj) VALUES
(1, '55.555.555/0001-55'),
(3, '66.666.666/0001-66'),
(5, '55.555.555/0001-55'),
(7, '77.777.777/0001-77'),
(9, '66.666.666/0001-66'),
(11, '55.555.555/0001-55'),
(13, '77.777.777/0001-77');

-- 22. Inserir dados em Possui (Cliente e ContaReceber)
INSERT INTO Possui (cnpj, id_conta) VALUES
('11.111.111/0001-11', 2),
('22.222.222/0001-22', 4),
('33.333.333/0001-33', 6),
('44.444.444/0001-44', 8),
('11.111.111/0001-11', 10),
('22.222.222/0001-22', 12);

-- Reabilita a verificação de chaves estrangeiras
SET FOREIGN_KEY_CHECKS = 1;
