CREATE TABLE public.city
(
    id bigint NOT NULL,
    id_ibge bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    state bigint NOT NULL,
    CONSTRAINT city_pkey PRIMARY KEY (id),
    CONSTRAINT uk_jc41w34sgl5d10tb3p8x81fwp UNIQUE (id_ibge),
    CONSTRAINT fkf8wncnsauusjgjgn29594r6nh FOREIGN KEY (state)
        REFERENCES public.state (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.city
    OWNER to scm;

CREATE SEQUENCE public.seq_city
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.seq_city
    OWNER TO scm;