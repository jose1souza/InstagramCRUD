INSERT INTO users VALUES
(DEFAULT,"Jose Carlos Souza","M","jose@gmail.com","55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251"),
(DEFAULT, "Ana Luísa", "F", "ana@mail.com","55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251");

INSERT INTO posts VALUES
(DEFAULT, "Olá do Jose Carlos", CURDATE(), 1),
(DEFAULT, "Olá da Ana Luísa", CURDATE(), 2),
(DEFAULT, "Estou de férias", CURDATE(), 2),
(DEFAULT, "Meu post", CURDATE(), 1),
(DEFAULT, "Apenas mais um post", CURDATE(), 2);

INSERT INTO messages (content, date_message, sender_id, receiver_id)
VALUES ('Oi Ana, tudo bem?', NOW(), 1, 2),
("Oi Jose, tudo, e você", NOW(), 2, 1),
("Tudo bem também", NOW(), 1, 2);
