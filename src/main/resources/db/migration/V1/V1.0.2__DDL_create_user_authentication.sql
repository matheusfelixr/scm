CREATE TABLE public.user_authentication
(
    id bigint NOT NULL,
    cancellation_date timestamp without time zone,
    cancellation_obs character varying(250) COLLATE pg_catalog."default",
    change_password boolean NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    is_admin boolean NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    cancellation_user bigint,
    CONSTRAINT user_authentication_pkey PRIMARY KEY (id),
    CONSTRAINT uk_1q6o90tfb7ideanseo8pb96du UNIQUE (user_name),
    CONSTRAINT uk_c2kyvps2301e69hnd2ost2l2l UNIQUE (email),
    CONSTRAINT fkrg9v7kkra4o76hwdyshgv1ugv FOREIGN KEY (cancellation_user)
        REFERENCES public.user_authentication (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.user_authentication
    OWNER to scm;

CREATE SEQUENCE public.seq_user_authentication
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.seq_user_authentication
    OWNER TO scm;