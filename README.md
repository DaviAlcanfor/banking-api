<div align="center">  
  <h1>Banking API</h1>

  ![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-6DB33F?style=for-the-badge&logo=spring)
  ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-316192?style=for-the-badge&logo=postgresql)
  ![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker)
  ![Java](https://img.shields.io/badge/Java-19-ED8B00?style=for-the-badge&logo=openjdk)
</div>

REST API for banking operations built with Spring Boot and PostgreSQL.

---

## Features

- Client management with encrypted passwords (BCrypt)
- Deposit, withdrawal and transfer between accounts
- Transaction history
- Credit and debit card management with tiers and variants
- Loan requests with simple and compound interest calculation
- Global exception handling with structured error responses
- API documentation with Swagger UI

---

## Running with Docker

```bash
docker-compose up --build
```

API → `http://localhost:8080`  
Swagger → `http://localhost:8080/swagger-ui/index.html`

---

## Running locally

**Requirements:** Java 19, Maven, PostgreSQL

1. Create the database:

```sql
CREATE DATABASE sistema_bancario;
```

2. Create `src/main/resources/application-local.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sistema_bancario
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
```

3. Set active profile to `local` in your IDE run configuration.

4. Run the application.

---

## Endpoints

### Clients `/banco`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/banco/cliente` | Create client |
| GET | `/banco/saldo` | Get balance |

### Transactions `/transacoes`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/transacoes/deposito` | Deposit |
| POST | `/transacoes/saque` | Withdrawal |
| POST | `/transacoes/transferencia` | Transfer between accounts |
| GET | `/transacoes/historico` | Transaction history |

### Cards `/cartoes`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/cartoes` | Create card |
| GET | `/cartoes` | List all cards |
| GET | `/cartoes/ativos` | List active cards |
| PATCH | `/cartoes/{id}/bloquear` | Block card |
| PATCH | `/cartoes/{id}/cancelar` | Cancel card |
| POST | `/cartoes/{id}/usar-limite` | Use credit limit |
| POST | `/cartoes/{id}/pagar-fatura` | Pay bill |

### Loans `/emprestimos`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/emprestimos` | Request loan |
| GET | `/emprestimos` | List loans |
| POST | `/emprestimos/{id}/pagar` | Pay installment |

---

## Card Tiers

| Tier | Credit Limit |
|------|-------------|
| N1 | R$ 500 |
| N2 | R$ 1,500 |
| N3 | R$ 3,000 |
| N4 | R$ 5,000 |

## Card Variants

`GOLD` · `PLATINUM` · `BLACK`

---

## Project Structure

```
src/main/java/com/exemplo/saudacao_api/
├── config/          # Security configuration
├── controller/      # REST controllers
├── exception/       # Custom exceptions and global handler
├── model/
│   ├── dto/         # Data transfer objects
│   ├── entity/      # JPA entities
│   └── types/       # Enums
├── repository/      # JPA repositories
├── response/        # API error response structure
└── service/         # Business logic
```
