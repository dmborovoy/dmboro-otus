CREATE SEQUENCE IF NOT EXISTS SEQ_USER_ID;

CREATE TABLE "user"
(
    id         BIGINT PRIMARY KEY DEFAULT nextval('SEQ_USER_ID' :: REGCLASS),
    account_id UUID,
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL,
    status     VARCHAR NOT NULL
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