CREATE DATABASE arquivo_financeiro_testes;

USE arquivo_financeiro_testes;

CREATE TABLE categorias
(
	id int unsigned not null auto_increment,
    categoria varchar(50) not null,
    data_inclusao date not null,
    PRIMARY KEY (id)
);

SELECT * FROM categorias;

CREATE TABLE gastos_gerais
(	
    data_registro date not null,
    descricao varchar(500),
    categoria_id int unsigned not null,
    valor double not null,
    id int unsigned not null auto_increment,
    id_mes_ano int unsigned not null,
    primary key (id),
    CONSTRAINT fk_gastos_gerais_categorias FOREIGN KEY (categoria_id) REFERENCES categorias (id),
    CONSTRAINT fk_gastos_gerais_mes_ano FOREIGN KEY (id_mes_ano) REFERENCES mes_ano (id_mes_ano)
);

SELECT * FROM gastos_gerais;

CREATE TABLE receitas
(
    descricao varchar(500),
    fonte varchar(100),
    data_recebimento date,
    valor double,
    id_mes_ano int unsigned not null,
    id int unsigned not null auto_increment,
    primary key (id),
    CONSTRAINT fk_receitas_mes_ano FOREIGN KEY (id_mes_ano) REFERENCES mes_ano (id_mes_ano)
);

SELECT * FROM receitas;

CREATE TABLE despesas_fixas
(
	descricao varchar(100),
    categoria_id int unsigned not null,
    valor double not null,
    status_registro varchar(30),
    data_registro date,
    observacao varchar(500),
    id_mes_ano int unsigned not null,
    id int unsigned not null auto_increment,
    PRIMARY KEY (id),
    CONSTRAINT fk_despesas_fixas_categorias FOREIGN KEY (categoria_id) REFERENCES categorias (id),
    CONSTRAINT fk_despesas_fixas_mes_ano FOREIGN KEY (id_mes_ano) REFERENCES mes_ano (id_mes_ano)
);

SELECT * FROM despesas_fixas;

CREATE TABLE despesas_variaveis
(
	descricao varchar(100),
    categoria_id int unsigned not null,
    valor_planejado double,
    valor_pago double,
    status_registro varchar(30),
    data_registro date,
    observacao varchar(500),
    id_mes_ano int unsigned not null,
    id int unsigned not null auto_increment,
    PRIMARY KEY (id),
    CONSTRAINT fk_despesas_variaveis_categorias FOREIGN KEY (categoria_id) REFERENCES categorias (id),
    CONSTRAINT fk_despesas_variaveis_mes_ano FOREIGN KEY (id_mes_ano) REFERENCES mes_ano (id_mes_ano)
);

SELECT * FROM despesas_variaveis;

CREATE TABLE gastos_categoria
(
    categoria_id int unsigned not null,
    total_gasto double,
    id_mes_ano int unsigned not null,
    id int unsigned not null auto_increment,
    PRIMARY KEY (id),
    CONSTRAINT fk_gastos_categoria_categorias FOREIGN KEY (categoria_id) REFERENCES categorias (id),
    CONSTRAINT fk_gastos_categoria_mes_ano FOREIGN KEY (id_mes_ano) REFERENCES mes_ano (id_mes_ano)
);

SELECT * FROM gastos_categoria;

CREATE TABLE totais
(
    valor_disponivel double,
    despesas double,
    receitas double,
    a_pagar double,
    id_mes_ano int unsigned not null,
    id int unsigned not null auto_increment,
    PRIMARY KEY (id),
    CONSTRAINT fk_totais_mes_ano FOREIGN KEY (id_mes_ano) REFERENCES mes_ano (id_mes_ano)
);

SELECT * FROM totais;

CREATE TABLE mes_ano
(
	id_mes_ano int unsigned not null auto_increment,
    mes_ano VARCHAR(30) not null,
    data_referencia date not null,
    PRIMARY KEY (id_mes_ano)
);

SELECT * FROM mes_ano;
