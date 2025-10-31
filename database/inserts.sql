INSERT INTO users VALUES
(DEFAULT,"Jose Carlos Souza","M","jose@gmail.com"),
(DEFAULT, "Luis Ricardo", "M", "luis@mail.com"),
(DEFAULT, "Lucas Rosa", "M", "lucas@mail.com"),
(DEFAULT, "Ana Luísa", "F", "ana@mail.com"),
(DEFAULT, "Luiz Felipe", "M", "luiz@mail.com"),
(DEFAULT, "Brian Martins", "M", "brian@mail.com");

INSERT INTO posts VALUES
(DEFAULT, "Olá do Luis Ricardo", CURDATE(), 1),
(DEFAULT, "Olá do Lucas Rosa", CURDATE(), 2),
(DEFAULT, "Olá da Ana Luísa", CURDATE(), 3),
(DEFAULT, "Olá do Luiz Felipe", CURDATE(), 4),
(DEFAULT, "Olá do Luiz Felipe", CURDATE(), 5);

INSERT INTO messages (content, date_message, sender_id, receiver_id)
VALUES ('Oi Ana, tudo bem?', NOW(), 1, 4),
("Oi Jose, tudo, e você", NOW(), 4, 1),
("Tudo bem também", NOW(), 1, 4);

UPDATE messages 
SET 
    content = 'Oi Ana,tudo bom?',
    date_message = NOW(),
    sender_id = 1,
    receiver_id = 4
WHERE
    id_message = 1; 
START TRANSACTION;
select * from messages;
ROLLBACK;