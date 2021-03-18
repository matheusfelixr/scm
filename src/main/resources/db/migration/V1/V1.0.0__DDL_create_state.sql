CREATE TABLE public.state
(
    id bigint NOT NULL,
    id_ibge character varying(255) COLLATE pg_catalog."default" NOT NULL,
    uf character varying(2) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT state_pkey PRIMARY KEY (id),
    CONSTRAINT uk_8qi71cu858v2yonnelgc5ugle UNIQUE (id_ibge),
    CONSTRAINT uk_c6e5fgj9gn2tnlj463v32be4h UNIQUE (uf)
)

TABLESPACE pg_default;

ALTER TABLE public.state
    OWNER to scm;

CREATE SEQUENCE public.seq_state
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.seq_state
    OWNER TO scm;