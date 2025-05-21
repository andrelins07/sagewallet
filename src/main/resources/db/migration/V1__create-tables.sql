CREATE TABLE tb_usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    salario DECIMAL(15, 2),
    ocupacao VARCHAR(255)
);

CREATE TABLE tb_categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE tb_lancamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    data DATE NOT NULL,
    valor DECIMAL(15, 2) NOT NULL,
    categoria_id INT,
    tipo ENUM('RECEITA', 'DESPESA') NOT NULL,
    usuario_id INT NOT NULL,

    CONSTRAINT fk_lancamento_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES tb_categorias(id),

    CONSTRAINT fk_lancamento_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES tb_usuarios(id)
        ON DELETE CASCADE
);

