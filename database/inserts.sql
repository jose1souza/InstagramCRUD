INSERT INTO users (user_name, gender, email, user_password) VALUES
("Jose Carlos Souza","M","jose@gmail.com","55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251"),
("Ana Luísa", "F", "ana@mail.com","55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251"),
("Johann E Sacconi F",	"M"	,"johannsacconi@gmail.com",	"2240e98c15f4815e5f2e24e9ebad6fa816893139b55219ba9d869f8824ac72e5");

INSERT INTO posts (content, post_date,user_id) VALUES
("Olá do Jose Carlos", CURDATE(), 1),
("Olá da Ana Luísa", CURDATE(), 2),
("Meu post", CURDATE(), 1),
("Apenas mais um post", CURDATE(), 2);

INSERT INTO messages (content, date_message,sender_id,receiver_id) 
VALUES ('Oi Ana, tudo bem?', NOW(), 1, 2),
("Oi Jose, tudo, e você", NOW(), 2, 1),
("Tudo bem também", NOW(), 1, 2);
