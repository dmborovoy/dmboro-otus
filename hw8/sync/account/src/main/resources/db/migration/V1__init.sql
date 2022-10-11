CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE "account"
(
    id          UUID PRIMARY KEY            DEFAULT ordering.gen_random_uuid(),
    user_id     UUID    NOT NULL,
    balance     numeric(20, 9) not null default 0.0,
    status      VARCHAR NOT NULL,
    description VARCHAR,
    created_on  TIMESTAMP WITHOUT TIME ZONE default now(),
    modified_on TIMESTAMP WITHOUT TIME ZONE
);