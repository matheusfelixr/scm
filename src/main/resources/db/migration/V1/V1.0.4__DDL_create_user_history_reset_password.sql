CREATE TABLE public.history_reset_password
(
    id bigint NOT NULL,
    date timestamp without time zone NOT NULL,
    ip character varying(255) COLLATE pg_catalog."default",
    user_authentication bigint,
    CONSTRAINT history_reset_password_pkey PRIMARY KEY (id),
    CONSTRAINT fkt6oj39xvl1ytoby3bmtrrm8ga FOREIGN KEY (user_authentication)
        REFERENCES public.user_authentication (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.history_reset_password
    OWNER to scm;

CREATE SEQUENCE public.seq_history_reset_password
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.seq_history_reset_password
    OWNER TO scm;