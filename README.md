# 🏗️ Horizonte Construtora — Plataforma Web

---

## 🌐 Acesse o projeto
- Site: http://www.horizonteconstrutora.newbie4.com.br  
- API: http://18.191.214.57:8080  

---

## 📌 Sobre o Projeto

A **Horizonte Construtora** é uma plataforma web desenvolvida com foco na apresentação institucional de empreendimentos imobiliários, integrada a uma **API própria em Java** para gerenciamento de dados e autenticação de usuários.

Arquitetura:
- Front-end: WordPress
- Back-end: Spring Boot (Java)
- Banco: PostgreSQL
- Cloud: AWS EC2

---

## ⚙️ Tecnologias

### Backend
- Java
- Spring Boot
- Spring Security
- JWT
- JPA / Hibernate
- BCrypt

### Frontend
- WordPress (Hello Elementor)

### Plugins
- Elementor
- MetForm
- UAE
- Yoast SEO
- WP Mail SMTP
- Search & Filter
- Click to Chat

---

## 🔐 Autenticação

POST /auth/login

```json
{
  "email": "admin@horizonte.com.br",
  "password": "admin"
}
```

Resposta:
```json
{
  "token": "SEU_TOKEN"
}
```

---

## 👤 Usuários de Teste

Admin:
- admin@horizonte.com.br
- admin

Cliente:
- cliente@horizonte.com.br
- cliente
- CPF: 11122233344

---

## 🔗 Endpoints

### Auth
- POST /auth/login

### Users
- POST /users/client
- POST /users/admin
- GET /users
- GET /users/{id}
- GET /users/me
- PUT /users/{id}

### Real States
- POST /api/real-states
- GET /api/real-states/all
- GET /api/real-states/name/{name}
- PUT /api/real-states/{id}

### Units
- POST /units
- GET /units
- GET /units/status/{status}
- GET /units/realstate
- PUT /units/{id}

### Contracts
- POST /contract
- GET /contracts/all
- GET /contracts/user/{cpf}
- GET /contracts/realstate/{name}
- GET /contracts/me
- PATCH /contracts/{id}

---

## 🚀 Deploy

AWS EC2:
- IP: 18.191.214.57
- Porta: 8080
- PostgreSQL local

---

## 🧪 Testes

Postman

<img width="1743" height="604" alt="image" src="https://github.com/user-attachments/assets/3aac8872-542c-4841-b2b3-e96e3bb6566a" />
<img width="723" height="286" alt="image" src="https://github.com/user-attachments/assets/975ab500-300e-472a-a824-2c85f8851c14" />
<img width="814" height="374" alt="image" src="https://github.com/user-attachments/assets/c7781036-95b4-40b0-a913-64322b6a085f" />


---

## 📸 Imagens

<img width="2553" height="880" alt="image" src="https://github.com/user-attachments/assets/b8b28e57-837d-4dac-afe7-18bead0c36cf" />
<img width="2543" height="877" alt="image" src="https://github.com/user-attachments/assets/7e974b5e-82a4-4a25-9f8d-56a2d1aaa3eb" />
<img width="2542" height="873" alt="image" src="https://github.com/user-attachments/assets/bbedaf11-ac5b-458b-aac7-01aae18139c1" />

---

## 📊 Trello

https://trello.com/invite/b/69a8ca7cfaeb8ab4baad90fa/ATTIc030a12f9ac3ac0b884315480b4528b3B165E8F5/newbie4-desenvolvimento-web

---

## 🚧 Status

Em desenvolvimento

---

## 👨‍💻 Autor

Projeto para fins de estudo e aplicação real.
