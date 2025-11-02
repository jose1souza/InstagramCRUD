-- Querys teste durante o desenvolvimento
-- usando transações para não aplicar alterações no banco
USE instagram;
START TRANSACTION;
ROLLBACK;

SELECT *FROM users;
SELECT * FROM posts;
SELECT * FROM messages;

UPDATE messages 
SET 
    content = 'Oi Ana,tudo bom?',
    date_message = NOW(),
    sender_id = 1,
    receiver_id = 4
WHERE
    id_message = 1; 
    
DELETE FROM messages 
WHERE
    id_message = 5;
SELECT 
    *
FROM
    messages;
    
SELECT 
    id, nome, sexo, email, password_hash
FROM
    users
WHERE
    email = 'jose@gmail.com';