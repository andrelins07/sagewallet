ALTER TABLE tb_usuarios ADD COLUMN nome_usuario VARCHAR(255);

UPDATE tb_usuarios SET nome_usuario = CONCAT('usuario', id);

ALTER TABLE tb_usuarios
MODIFY COLUMN nome_usuario VARCHAR(255) NOT NULL,
ADD UNIQUE (nome_usuario),
ADD UNIQUE (email);
