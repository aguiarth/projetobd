CREATE DATABASE bd_fabrica_descartaveis;

CREATE TABLE Financeiro (
    id_financeiro INT AUTO_INCREMENT PRIMARY KEY,
    historico_lucro FLOAT,
    historico_prejuizo FLOAT,
    data_atualizacao DATE
);

CREATE TABLE Conta (
    id_conta INT AUTO_INCREMENT PRIMARY KEY,
    id_financeiro INT,
    data_emissao DATE,
    data_vencimento DATE,
    valor_total FLOAT,
    status VARCHAR(45),
    FOREIGN KEY (id_financeiro) REFERENCES Financeiro(id_financeiro)
);

CREATE TABLE ContaReceber (
    id_conta INT AUTO_INCREMENT PRIMARY KEY,
    FOREIGN KEY (id_conta) REFERENCES Conta(id_conta)
);

CREATE TABLE Cliente (
    cnpj VARCHAR(45) PRIMARY KEY,
    razao_social VARCHAR(45),
    rua VARCHAR(45),
    numero VARCHAR(45),
    cidade VARCHAR(45),
    cep VARCHAR(45),
    email VARCHAR(45)
);

create TABLE  Telefone_cliente (
	cnpj VARCHAR(45),
	telefone_cliente VARCHAR(45),
	FOREIGN KEY (cnpj) REFERENCES Cliente(cnpj),
	primary key (cnpj, telefone_cliente)
);

CREATE TABLE Possui (
    cnpj VARCHAR(45),
    id_conta INT,
    FOREIGN KEY (cnpj) REFERENCES Cliente(cnpj),
    FOREIGN KEY (id_conta) REFERENCES ContaReceber(id_conta),
    PRIMARY KEY (cnpj, id_conta)
);

CREATE TABLE NotaFiscal (
    chave VARCHAR(45) PRIMARY KEY,
    valor_imposto FLOAT,
    data_emissao_nota DATE
);

CREATE TABLE Pedido (
    numero VARCHAR(45) PRIMARY KEY,
    chave VARCHAR(45),
    data_emissao DATE,
    valor_total FLOAT,
    status VARCHAR(45),
    forma_pagamento VARCHAR(45),
    FOREIGN KEY (chave) REFERENCES NotaFiscal(chave)
);

CREATE TABLE Realiza (
    cnpj VARCHAR(45),
    numero VARCHAR(45),
    FOREIGN KEY (cnpj) REFERENCES Cliente(cnpj),
    FOREIGN KEY (numero) REFERENCES Pedido(numero),
    PRIMARY KEY (cnpj, numero)
);

CREATE TABLE Expedicao (
    id_expedicao INT AUTO_INCREMENT PRIMARY KEY,
    data_expedicao DATE,
    hora_expedicao TIME,
    status VARCHAR(45)
);

CREATE TABLE Envia (
    numero VARCHAR(45),
    id_expedicao INT,
    FOREIGN KEY (numero) REFERENCES Pedido(numero),
    FOREIGN KEY (id_expedicao) REFERENCES Expedicao(id_expedicao),
    PRIMARY KEY (numero, id_expedicao)
);

CREATE TABLE EntregaTransporte (
    numero_entrega VARCHAR(45) PRIMARY KEY,
    id_expedicao INT,
    data_prevista DATE,
    data_saida DATE,
    data_entrega DATE,
    FOREIGN KEY (id_expedicao) REFERENCES Expedicao(id_expedicao)
);

CREATE TABLE ProdutoAcabado (
    id_produto INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(45),
    data_finalizacao DATE
);

CREATE TABLE Contem (
    numero VARCHAR(45),
    id_produto INT,
    FOREIGN KEY (numero) REFERENCES Pedido(numero),
    FOREIGN KEY (id_produto) REFERENCES ProdutoAcabado(id_produto),
    PRIMARY KEY (numero, id_produto)
);

CREATE TABLE OrdemProducao (
    id_ordem INT AUTO_INCREMENT PRIMARY KEY,
    id_dependente INT,
    id_requisitado INT,
    produto_fabricado VARCHAR(45),
    quantidade_produto INT,
    data_inicio DATE,
    data_final DATE,
    descricao VARCHAR(45),
    FOREIGN KEY (id_dependente) REFERENCES OrdemProducao(id_ordem),
    FOREIGN KEY (id_requisitado) REFERENCES OrdemProducao(id_ordem)
);

CREATE TABLE Gera (
    id_ordem INT,
    id_produto INT,
    FOREIGN KEY (id_ordem) REFERENCES OrdemProducao(id_ordem),
    FOREIGN KEY (id_produto) REFERENCES ProdutoAcabado(id_produto),
    PRIMARY KEY (id_ordem, id_produto)
);

CREATE TABLE MateriaPrima (
    id_materia_prima INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(45),
    data_validade DATE,
    custo_unitario FLOAT,
    custo_total FLOAT
);

CREATE TABLE Consome (
    id_materia_prima INT,
    id_ordem INT,
    FOREIGN KEY (id_materia_prima) REFERENCES MateriaPrima(id_materia_prima),
    FOREIGN KEY (id_ordem) REFERENCES OrdemProducao(id_ordem),
    PRIMARY KEY (id_materia_prima, id_ordem)
);

CREATE TABLE Estoque (
    id_estoque INT AUTO_INCREMENT PRIMARY KEY,
    tipo_movimentacao VARCHAR(45),
    data_movimentacao DATE,
    hora_movimentacao TIME
);

CREATE TABLE Lote (
    codigo VARCHAR(45) PRIMARY KEY,
    id_estoque INT,
    id_materia_prima INT,
    id_produto INT,
    custo VARCHAR(45),
    descricao VARCHAR(45),
    quantidade INT,
    data_validade DATE,
    FOREIGN KEY (id_estoque) REFERENCES Estoque(id_estoque),
    FOREIGN KEY (id_materia_prima) REFERENCES MateriaPrima(id_materia_prima),
    FOREIGN KEY (id_produto) REFERENCES ProdutoAcabado(id_produto)
);

CREATE TABLE Fornecedor (
    cnpj VARCHAR(45) PRIMARY KEY,
    razao_social VARCHAR(45),
    endereco VARCHAR(45),
    telefone VARCHAR(45),
    condicoes_pagamento VARCHAR(45)
);

CREATE TABLE Vincula (
    cnpj VARCHAR(45),
    codigo VARCHAR(45),
    FOREIGN KEY (cnpj) REFERENCES Fornecedor(cnpj),
    FOREIGN KEY (codigo) REFERENCES Lote(codigo),
    PRIMARY KEY (cnpj, codigo)
);

CREATE TABLE ContaPagar (
    id_conta INT,
    cnpj VARCHAR(45),
    FOREIGN KEY (id_conta) REFERENCES Conta(id_conta),
    FOREIGN KEY (cnpj) REFERENCES Fornecedor(cnpj),
    PRIMARY KEY (id_conta, cnpj)
);
