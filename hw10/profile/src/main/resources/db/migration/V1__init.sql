CREATE SEQUENCE IF NOT EXISTS SEQ_USER_ID;
CREATE SEQUENCE IF NOT EXISTS SEQ_PERMISSION_ID;

CREATE TABLE "user"
(
    id          BIGINT PRIMARY KEY                   DEFAULT nextval('SEQ_USER_ID' :: REGCLASS),
    keycloak_id UUID,
    "login"     VARCHAR,
    first_name  VARCHAR                     NOT NULL,
    last_name   VARCHAR                     NOT NULL,
    status      VARCHAR                     NOT NULL,
    created_on  timestamp without time zone NOT NULL default now(),
    modified_on timestamp without time zone
);

CREATE TABLE "permission"
(
    id     BIGINT PRIMARY KEY DEFAULT nextval('SEQ_PERMISSION_ID' :: REGCLASS),
    "name" VARCHAR NOT NULL
);

CREATE TABLE "user_permission_map"
(
    user_id       BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    primary key (user_id, permission_id)
);

-- axon's required tables
create table token_entry
(
    processor_name varchar(255) not null,
    segment        integer      not null,
    owner          varchar(255),
    timestamp      varchar(255) not null,
    token          oid,
    token_type     varchar(255),
    primary key (processor_name, segment)
);

create table saga_entry
(
    saga_id         varchar(255) not null primary key,
    revision        varchar(255),
    saga_type       varchar(255),
    serialized_saga oid
);

create table association_value_entry
(
    id                bigint       not null primary key,
    association_key   varchar(255) not null,
    association_value varchar(255),
    saga_id           varchar(255) not null,
    saga_type         varchar(255)
);

