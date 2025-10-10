# ⚡ SageWallet - Gerenciando suas finanças com sabedoria

SageWallet é um sistema de gerenciamento financeiro, desenvolvido com tecnologias modernas e arquitetura escalável. A principal proposta é permitir que os usuarios façam o controle pessoal de suas finanças de forma compartilhada, com atualizações em tempo real. Desse modo, as finanças de toda uma família pode ser feita num único lugar, separado por seus usuarios independentes.


---

## 🚀 Funcionalidades

- ✅ CRUD
- ✅ Dashboard atualizado em tempo real com principais indicadores
- ✅ Notificações sobre lançamentos e compartilhamentos
- ✅ Lançamentos compartilhados
- ✅ Envio e recebimento de mensagens via WebSocket + RabbitMQ
- ✅ Entre outras...

---

## 🧱 Arquitetura



A aplicação backend é organizada em camadas:

- **Controller**: expõe APIs REST e WebSocket.
- **Service**: camada de lógica de negócio.
- **Repository**: persistência com Spring Data JPA.
- **DTOs e Mappers**: conversão de entidades e segurança de dados.



---

## 🛠️ Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot
- WebSocket (STOMP + JWT)
- MySQL
- Spring Security
- Flyway migrations

### Frontend
- HTML
- CSS
- TypeScript
- React

