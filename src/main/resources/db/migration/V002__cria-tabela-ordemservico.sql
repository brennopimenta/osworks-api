CREATE TABLE ordem_servico
(
    id bigserial NOT NULL,
    data_abertura timestamp without time zone,
    data_finalizacao timestamp without time zone,
    descricao character varying(255),
    preco numeric(19,2),
    status character varying(255),
    cliente_id bigint,
    CONSTRAINT pk_ordem_servico PRIMARY KEY (id),
    CONSTRAINT fk_ordem_servico_cliente FOREIGN KEY (cliente_id)
        REFERENCES cliente (id)
);