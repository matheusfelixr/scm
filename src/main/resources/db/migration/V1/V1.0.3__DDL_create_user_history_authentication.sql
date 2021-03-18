CREATE TABLE public.history_authentication
(
    id bigint NOT NULL,
    user_name character varying(255) COLLATE pg_catalog."default",
    date timestamp without time zone NOT NULL,
    ip character varying(255) COLLATE pg_catalog."default",
    observation character varying(255) COLLATE pg_catalog."default",
    user_authentication bigint,
    CONSTRAINT history_authentication_pkey PRIMARY KEY (id),
    CONSTRAINT fk6m4aglmkqtw9b11npn6yrs8qm FOREIGN KEY (user_authentication)
        REFERENCES public.user_authentication (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.history_authentication
    OWNER to scm;

CREATE SEQUENCE public.seq_history_authentication
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.seq_history_authentication
    OWNER TO scm;