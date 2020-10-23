CREATE TABLE cliente
(
    id bigint NOT NULL,
    email character varying(255),
    nome character varying(255),
    fone character varying(255),
    CONSTRAINT pk_cliente PRIMARY KEY (id)
);