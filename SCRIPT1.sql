CREATE TABLE exame_nf (
	IDNF serial PRIMARY key,
	NUMERO INT NOT NULL,
	DATACADASTRO DATA NULL,
	TOTALGERAL INT NULL
);

CREATE TABLE exame_itemnf (
	IDITEMNF serial PRIMARY Key,
	IDNF INT  NOT NULL,
	IDPRODUTO INT NOT NULL ,
	QTDE INT Not NULL,
	VALOR FLOAT Not NULL,
)