CREATE TABLE tb_notificacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensagem VARCHAR(255) NOT NULL,
    data_criacao DATE NOT NULL,
    usuario_id INT NOT NULL,

    CONSTRAINT fk_notificacao_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES tb_usuarios(id)
);