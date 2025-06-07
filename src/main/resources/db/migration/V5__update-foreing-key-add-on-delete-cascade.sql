ALTER TABLE tb_compartilhamentos_lancamentos
  DROP FOREIGN KEY tb_compartilhamentos_lancamentos_ibfk_1,
  DROP FOREIGN KEY tb_compartilhamentos_lancamentos_ibfk_2;

ALTER TABLE tb_notificacoes
  DROP FOREIGN KEY fk_notificacao_usuario;

ALTER TABLE tb_compartilhamentos_lancamentos
  ADD CONSTRAINT fk_compartilhamento_remetente
    FOREIGN KEY (remetente_id) REFERENCES tb_usuarios(id) ON DELETE CASCADE,
  ADD CONSTRAINT fk_compartilhamento_destinatario
    FOREIGN KEY (destinatario_id) REFERENCES tb_usuarios(id) ON DELETE CASCADE;

ALTER TABLE tb_notificacoes
  ADD CONSTRAINT fk_notificacao_usuario
    FOREIGN KEY (usuario_id) REFERENCES tb_usuarios(id) ON DELETE CASCADE;
