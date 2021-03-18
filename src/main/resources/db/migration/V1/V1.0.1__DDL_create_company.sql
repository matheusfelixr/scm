CREATE TABLE public.company
(
    id bigint NOT NULL,
    cep character varying(255) COLLATE pg_catalog."default",
    city character varying(255) COLLATE pg_catalog."default",
    full_address character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    phone character varying(255) COLLATE pg_catalog."default" NOT NULL,
    road character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT company_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.company
    OWNER to scm;


CREATE SEQUENCE public.seq_company
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.seq_company
    OWNER TO scm;