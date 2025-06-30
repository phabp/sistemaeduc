# SISTEMAEDUC - Backend

Este projeto consiste em um **sistema de gerenciamento escolar** desenvolvido com **Java** e **Spring Boot**, que tem como objetivo centralizar e facilitar o controle de usuários dentro de uma plataforma educacional. Ele permite o cadastramento de novos usuários/login com usuário existente.
Ao ingressar no sistema, o usuário tem acesso a algumas funcionalidades, tais como cadastrar alunos, escolas e professores, vinculá-los entre si e também consultar dados a respeito de cada um individualmente ou até mesmo listá-los de form integral. Tudo para haja um básico controle de um sistema escolar.

A API foi desenvolvida com foco em boas práticas, organização modular e segurança de dados, utilizando criptografia de senhas com BCrypt, persistência com PostgreSQL e arquitetura RESTful.

---

## 📌 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Spring Security (BCrypt)**
- **PostgreSQL**
- **Maven**

---

## 📌 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Spring Security (BCrypt)**
- **PostgreSQL**
- **Maven**

---

## 🚀 Como Executar o Projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/sistemaeduc.git
```

### 2. Configurar o banco de dados PostgreSQL

Crie um banco de dados com o nome desejado (por exemplo: `sistemaeduc`) e edite o arquivo `application.properties` com suas credenciais:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sistemaeduc
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8082
```

---

### 3. Rodar a aplicação

Abra o projeto em sua IDE (Eclipse, IntelliJ etc.) e execute a classe principal:

```java
com.sistemaeduc.SistemaeducApplication
```

A aplicação estará disponível em:

```
http://localhost:8082
```

---

## ✨ Aspectos relevantes:

- ✅ Cadastro de novos usuários
- 🔒 Armazenamento de senhas com segurança (BCrypt)
- 🔍 Validação de CPF único
- 🔑 Login com verificação de credenciais
- 📡 API REST pronta para integração

---

## 📂 Estrutura de Pacotes

```
src/main/java/com/sistemaeduc/
├── controllers/     # Controladores REST
├── entities/        # Entidades JPA
├── repositories/    # Interfaces para acesso ao banco
└── SistemaeducApplication.java
```

---

## 🔒 Segurança

- Criptografia de senhas com `BCryptPasswordEncoder`
- Proteção contra duplicidade de usuários pelo CPF
- Suporte a CORS com `@CrossOrigin` para permitir requisições de domínios externos

---

## 📄 Endpoints Principais

| Método | Rota                        | Descrição                     |
|--------|-----------------------------|-------------------------------|
| POST   | `/api/usuarios/cadastro`    | Cadastra um novo usuário      |
| POST   | `/api/usuarios/login`       | Realiza login do usuário      |

---

## 👤 Autor

Paulo Henrique A. de B.Pereira

---

