CREATE TABLE tb_compartilhamentos_lancamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    remetente_id INT NOT NULL,
    destinatario_id INT NOT NULL,
    data_compartilhamento DATE,
    status ENUM('PENDENTE', 'APROVADO', 'RECUSADO') DEFAULT 'PENDENTE',
    data_solicitacao DATE,

    UNIQUE (remetente_id, destinatario_id),
    FOREIGN KEY (remetente_id) REFERENCES tb_usuarios(id),
    FOREIGN KEY (destinatario_id) REFERENCES tb_usuarios(id)
);
