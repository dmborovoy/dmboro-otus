CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE "good"
(
    id          UUID PRIMARY KEY            DEFAULT ordering.gen_random_uuid(),
    "name"      varchar NOT NULL,
    count       INT                         default 0,
    department  VARCHAR,
    description VARCHAR,
    created_on  TIMESTAMP WITHOUT TIME ZONE default now(),
    modified_on TIMESTAMP WITHOUT TIME ZONE
);