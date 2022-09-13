CREATE SEQUENCE IF NOT EXISTS SEQ_USER_ID;

CREATE TABLE "user"
(
    id         BIGINT PRIMARY KEY DEFAULT nextval('SEQ_USER_ID' :: REGCLASS),
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL
);


CREATE TABLE "request"
(
    id         uuid PRIMARY KEY,
    body       VARCHAR,
    created_on timestamp without time zone
);