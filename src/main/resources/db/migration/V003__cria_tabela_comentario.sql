CREATE TABLE comentario
(
    id bigint NOT NULL,
    ordem_servico_id bigint NOT NULL,
    descricao character varying(255),
    data_envio timestamp without time zone,

    CONSTRAINT pk_comentario PRIMARY KEY (id),
    CONSTRAINT fk_comentario_ordem_servico FOREIGN KEY (ordem_servico_id)
        REFERENCES ordem_servico (id)
);