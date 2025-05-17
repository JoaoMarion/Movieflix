# 🎬 MovieFlix API

A **MovieFlix API** é uma aplicação backend desenvolvida em **Java + Spring Boot** para catálogos de filmes que gerencia autenticação de usuários, sistema de login com tokens JWT, controle de acesso com refresh token, e funcionalidades relacionadas a plataforma de filmes.

---

## 🚀 Funcionalidades

- ✅ Registro de usuários com verificação por e-mail
- 🔐 Login e autenticação via JWT
- 🔁 Refresh token para renovação de sessões
- 👤 Gerenciamento de usuários
- 🎫 Proteção de rotas via roles (usuário/admin)
- 🔍 Validação de token para controle de acesso
- 📬 Envio de e-mail com código de verificação
- 💰 Atributo dinheiro associado ao usuário (ex: para aluguéis de filmes)

---

## 🛠️ Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Security
- JWT (com Auth0)
- JPA + Hibernate
- PostgreSQL
- Flyway (migrações)
- Mail Sender (JavaMail)
- Lombok
- Maven

---

## 📌 Principais Endpoints


### 🔐 Rotas de Autenticação

- **POST /auth/register**  
  Registra um novo usuário.

- **POST /auth/login**  
  Realiza o login e retorna os tokens de acesso e refresh.

- **POST /auth/refresh**  
  Gera um novo token de acesso a partir do refresh token.

- **GET /auth/verifyToken**  
  Verifica se o token de acesso ainda é válido.


---

## 🔐 Segurança

A API utiliza:

- **JWT** no header Authorization para autenticação
- **Refresh token** persistente no banco de dados
- **ControllerAdvice** para tratamento de erros personalizados
- **Spring Security** com configuração customizada para proteger endpoints

---

## 🧪 Como executar localmente

1. Clone o repositório:
   ```bash
   git clone https://github.com/JoaoMarion/Movieflix.git
