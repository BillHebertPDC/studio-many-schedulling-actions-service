-- Dados iniciais idempotentes para H2 persistente (jdbc:h2:file)
-- Agendamentos de teste so sao inseridos quando a tabela esta vazia.

-- ============================================================
-- Templates de mensagem por status (editaveis pelo H2 console)
-- ============================================================
MERGE INTO template_mensagem (status, template) KEY(status) VALUES
  ('AGUARDANDO_SINAL', 'Olá! Seu agendamento está aguardando o sinal para a data *%s*. Fique atento!'),
  ('CONFIRMADO_CLIENTE', 'Olá! Seu agendamento foi *confirmado* para *%s*. Qualquer dúvida, estamos à disposição.'),
  ('EM_ESPERA_PROFISSIONAL', 'Olá! Seu agendamento está *aguardando confirmação do profissional* para *%s*. Avisamos assim que confirmar.'),
  ('CONFIRMADO_PROFISSIONAL', 'Olá! Seu agendamento foi *confirmado pelo profissional* para *%s*. Até lá!'),
  ('CONFIRMADO_REAGENDAMENTO_CLIENTE', 'Olá! Seu *reagendamento* foi confirmado para *%s*. Obrigado!'),
  ('CONFIRMADO_REAGENDAMENTO_PROFISSIONAL', 'Olá! O *reagendamento* do profissional foi confirmado para *%s*.'),
  ('RECUSADO_REAGENDAMENTO_CLIENTE', 'Olá! O *reagendamento* foi recusado. Manteremos o horário de *%s*.'),
  ('RECUSADO_REAGENDAMENTO_PROFISSIONAL', 'Olá! O *reagendamento* foi recusado pelo profissional. Manteremos o horário de *%s*.'),
  ('REAGENDAR_CLIENTE', 'Olá! Precisamos *reagendar* seu horário de *%s*. Entraremos em contato.'),
  ('REAGENDAR_PROFISSIONAL', 'Olá! O profissional solicitou *reagendamento* do seu horário de *%s*.'),
  ('RECUSADO_REVALIDAR', 'Olá! Precisamos *revalidar* seu agendamento de *%s*. Entraremos em contato.'),
  ('CHECKIN', 'Olá! Você fez *check-in* para o agendamento de *%s*. Logo iniciaremos o atendimento.'),
  ('EM_ATENDIMENTO', 'Olá! Seu atendimento de *%s* está em andamento.'),
  ('CONCLUIDO', 'Olá! Agradecemos a preferência! Seu atendimento de *%s* foi *concluído*.'),
  ('POS_ATENDIMENTO', 'Olá! Esperamos que tenha gostado do seu atendimento de *%s*. Sua opinião é importante para nós.'),
  ('NO_SHOW', 'Olá! Não foi possível confirmar sua presença no agendamento de *%s*. Podemos reagendar?'),
  ('CANCELADO_CLIENTE', 'Olá! Seu agendamento de *%s* foi *cancelado*, conforme solicitado.'),
  ('CANCELADO_PROFISSIONAL', 'Olá! Seu agendamento de *%s* foi *cancelado pelo profissional*. Lamentamos o inconveniente.'),
  ('CANCELADO_AUTOMATICAMENTE', 'Olá! Seu agendamento de *%s* foi *cancelado automaticamente*. Podemos reagendar?'),
  ('RECUSADO_CLIENTE', 'Olá! Seu agendamento de *%s* foi *recusado*. Podemos ajudar com um novo horário?');

-- ============================================================
-- Agendamentos de teste (somente se a tabela estiver vazia)
-- ============================================================
INSERT INTO agendamento (id_cliente, id_profissional, data_agendada, status, telefone_cliente, criado_em, atualizado_em)
SELECT v.id_cliente, v.id_profissional, v.data_agendada, v.status, v.telefone_cliente, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM (VALUES
  (1, 1, TIMESTAMP '2026-09-18 10:00:00', 'CONFIRMADO_CLIENTE', '5511944407845'),
  (2, 1, TIMESTAMP '2026-09-18 11:00:00', 'EM_ESPERA_PROFISSIONAL', '5511944407845'),
  (3, 2, TIMESTAMP '2026-09-18 14:00:00', 'CONFIRMADO_PROFISSIONAL', '5511944407845'),
  (4, 2, TIMESTAMP '2026-09-18 09:00:00', 'EM_ATENDIMENTO', '5511944407845'),
  (5, 3, TIMESTAMP '2026-09-18 10:30:00', 'CONCLUIDO', '5511944407845'),
  (6, 1, TIMESTAMP '2026-09-18 08:00:00', 'POS_ATENDIMENTO', '5511944407845'),
  (7, 3, TIMESTAMP '2026-09-18 15:00:00', 'CANCELADO_CLIENTE', '5511944407845'),
  (8, 2, TIMESTAMP '2026-09-18 11:00:00', 'RECUSADO_CLIENTE', '5511944407845'),
  (9, 1, TIMESTAMP '2026-09-18 14:00:00', 'REAGENDAR_CLIENTE', '5511944407845'),
  (10, 2, TIMESTAMP '2026-09-18 09:00:00', 'RECUSADO_REVALIDAR', '5511944407845')
) AS v(id_cliente, id_profissional, data_agendada, status, telefone_cliente)
WHERE NOT EXISTS (SELECT 1 FROM agendamento);