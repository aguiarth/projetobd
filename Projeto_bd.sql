create database bd_fabrica_descartaveis;

create table Financeiro(
	IdFinanceiro int primary key,
	historico_lucro float,
	historico_pejuizo float,
	dataAtualizacao DATE
);

create table Contas(
	IdConta int primary key,
	IdFinanceiro int,
	dataEmissao date,
	dataVencimento date,
	valorTotal float,
	status VARCHAR(45),
	foreign key (IdFinanceiro) references Financeiro(IdFinanceiro)
);

create table Receber(
	IdConta int,
	foreign key (IdConta) references Contas(IdConta),
	primary key (IdConta)
);

create table Cliente(
	cnpj VARCHAR(45) primary key,
	razaoSocial VARCHAR(45),
	rua VARCHAR(45),
	numero VARCHAR(45),
	cidade VARCHAR(45),
	cep VARCHAR(45),
	telefonePessoal VARCHAR(45),
	telefoneResidencial VARCHAR(45),
	email VARCHAR(45)
);

create table Possui(
	cnpj VARCHAR(45),
	IdConta int,
	foreign key (cnpj) references Cliente(cnpj),
	foreign key (IdConta) references Receber(IdConta),
	primary key (cnpj, IdConta)
);

create table NotaFiscal(
	chave VARCHAR(45) primary key,
	valorImposto float,
	dataEmissaoNota date
);

create table Pedido(
	numero VARCHAR(45) primary key,
	chave VARCHAR(45),
	dataEmissao date,
	valorTotal float,
	status VARCHAR(45),
	formaPagamento VARCHAR(45),
	foreign key (chave) references NotaFiscal(chave)
);

create table Realiza(
	cnpj VARCHAR(45),
	numero VARCHAR(45),
	foreign key (cnpj) references Cliente(cnpj),
	foreign key (numero) references Pedido(numero),
	primary key (cnpj, numero)
);

create table Expedicao(
	IdExpedicao int primary key,
	dataExpedicao date,
	horaExpedicao time,
	status VARCHAR(45)
);

create table Envia(
	numero VARCHAR(45),
	IdExpedicao int,
	foreign key (numero) references Pedido(numero),
	foreign key (IdExpedicao) references Expedicao(IdExpedicao),
	primary key (numero, IdExpedicao)
);

create table EntregaTransporte(
	numero_entrega VARCHAR(45) primary key,
	IdExpedicao int,
	dataPrevista date,
	dataSaida date,
	dataEntrega date,
	foreign key (IdExpedicao) references Expedicao(IdExpedicao)
);

create table ProdutoAcabado(
	IdProduto int primary key,
	descricao VARCHAR(45),
	dataFinalizacao date
);

create table Contem(
	numero VARCHAR(45),
	IdProduto int,
	foreign key (numero) references Pedido(numero),
	foreign key (IdProduto) references ProdutoAcabado(IdProduto),
	primary key (numero, IdProduto)
);

create table OrdemProducao(
	IdOrdem int primary key,
	IdDependente int,
	IdRequisitado int,
	produtoFabricado VARCHAR(45),
	quantidadeProduto int,
	dataInicio date,
	dataFinal date,
	descricao VARCHAR(45),
	foreign key (IdDependente) references OrdemProducao(IdOrdem),
	foreign key (IdRequisitado) references OrdemProducao(IdOrdem)
);

create table Gera(
	IdOrdem int,
	IdProduto int,
	foreign key (IdOrdem) references OrdemProducao(IdOrdem),
	foreign key (IdProduto) references ProdutoAcabado(IdProduto),
	primary key (IdOrdem, IdProduto)
);

create table MateriaPrima(
	IdMateriaPrima int primary key,
	descricao VARCHAR(45),
	dataValidade date,
	custoUnitario float,
	custoTotal float
);

create table Consome(
	IdMateriaPrima int,
	IdOrdem int,
	foreign key (IdMateriaPrima) references MateriaPrima(IdMateriaPrima),
	foreign key (IdOrdem) references OrdemProducao(IdOrdem),
	primary key (IdMateriaPrima, IdOrdem)
);

create table Estoque(
	IdEstoque int primary key,
	tipoMovimentacao VARCHAR(45),
	dataMovimentacao date,
	horaMovimentacao time
);

create table Lote(
	codigo VARCHAR(45) primary key,
	IdEstoque int,
	IdMateriaPrima int,
	IdProduto int,
	custo VARCHAR(45),
	descricao VARCHAR(45),
	quantidade int,
	dataValidade date,
	foreign key (IdEstoque) references Estoque(IdEstoque),
	foreign key (IdMateriaPrima) references MateriaPrima(IdMateriaPrima),
	foreign key (IdProduto) references ProdutoAcabado(IdProduto)
);

create table Fornecedor(
	cnpj VARCHAR(45) primary key,
	razaoSocial VARCHAR(45),
	endereco VARCHAR(45),
	telefone VARCHAR(45),
	condicoesPagamento VARCHAR(45)
);

create table Vincula(
	cnpj VARCHAR(45),
	codigo VARCHAR(45),
	foreign key (cnpj) references Fornecedor(cnpj),
	foreign key (codigo) references Lote(codigo),
	primary key (cnpj, codigo)
);

create table Pagar(
	IdConta int,
	cnpj VARCHAR(45),
	foreign key (IdConta) references Contas(IdConta),
	foreign key (cnpj) references Fornecedor(cnpj),
	primary key (IdConta, cnpj)
);
