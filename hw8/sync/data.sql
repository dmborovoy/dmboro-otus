INSERT INTO account.account(id, user_id, balance, status, created_on)
VALUES ('0fbcfd2f-b2b3-451e-95a8-cae470ed1479', 'c654cffd-8e90-4884-bffd-e46ac489d9da', 100.22, 'NORMAL', now());

INSERT INTO delivery.good(id, "name", count, department, created_on)
VALUES ('0ef9e303-3008-4720-a697-5fccb775f1de', 'shoes', 17, 'SHOP', now()),
       ('b2750db8-562b-466a-b12e-0292ab0fcf5c', 'pencil', 217, 'SHOP', now()),
       ('dfe54b18-7a31-4c31-b10a-5ecb69313fe5', 'microwave', 11, 'STOCK', now());