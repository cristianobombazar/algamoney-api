CREATE TABLE categoria(
	id serial,
	nome character varying(50) not null,
	
	CONSTRAINT pk_categoria PRIMARY KEY (id)	
);

INSERT INTO categoria(nome) VALUES('Lazer');
INSERT INTO categoria(nome) VALUES('Alimentação');
INSERT INTO categoria(nome) VALUES('Supermercado');
INSERT INTO categoria(nome) VALUES('Farmácia');
INSERT INTO categoria(nome) VALUES('Outros');