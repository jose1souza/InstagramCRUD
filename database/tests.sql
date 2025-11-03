-- Querys teste durante o desenvolvimento
-- usando transações para não aplicar alterações no banco
USE instagram;
START TRANSACTION;
ROLLBACK;

SELECT *FROM users;
SELECT * FROM posts;
SELECT * FROM messages;

-- Update
UPDATE messages 
SET 
    content = 'Oi Ana,tudo bom?',
    date_message = NOW(),
    sender_id = 1,
    receiver_id = 4
WHERE
    id_message = 1; 

-- Delete
DELETE FROM messages 
WHERE
    id_message = 5;
SELECT 
    *
FROM
    messages;

-- Usuário com email jose
SELECT 
    id, nome, sexo, email, password_hash
FROM
    users
WHERE
    email = 'jose@gmail.com';

-- Mensagens recebidas pela ana
SELECT 
    id_message,
    content,
    date_message,
    sender_id,
    receiver_id
FROM
    messages
WHERE
    receiver_id = 2
    ORDER BY date_message DESC;