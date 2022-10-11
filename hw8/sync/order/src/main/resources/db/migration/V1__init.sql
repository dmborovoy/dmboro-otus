CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE "ordering"
(
    id             UUID PRIMARY KEY            DEFAULT gen_random_uuid(),
    transaction_id UUID,
    user_id        UUID    NOT NULL,
    status         VARCHAR NOT NULL,
    description    VARCHAR, -- maybe error in case transaction failed
    created_on     TIMESTAMP WITHOUT TIME ZONE default now(),
    modified_on    TIMESTAMP WITHOUT TIME ZONE,
    completed_on   TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "item"
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    good_id  UUID NOT NULL,
    count    INT  NOT NULL,
    order_id UUID NOT NULL,
    CONSTRAINT item_order_fk FOREIGN KEY (order_id) REFERENCES "ordering" (id)
);

create table saga
(
    id             uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    stage          VARCHAR NOT NULL,--NEW|ORDER|ACCOUNT|STOCK|DONE|REVERTED|FAILED -- this is total flow status, FAILED - means we was unable to rollback
    transaction_id uuid    NOT NULL,
--     order_id       uuid,
    "type"         varchar NOT NULL,--NORMAL|ROLLBACK kind of direction
    status         varchar,--OK,FAILED,PENDING - status of current stage
    "error"        varchar
);


