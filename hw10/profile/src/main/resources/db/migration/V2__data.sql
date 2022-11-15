INSERT INTO permission (id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER'),
       (3, 'VIP_USER');
SELECT setval('SEQ_PERMISSION_ID', (SELECT MAX(id) FROM permission));