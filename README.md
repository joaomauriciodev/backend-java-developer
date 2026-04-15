# cmanager-service

API REST para gerenciamento de usuários e séries de TV, integrada com a API pública [TVMaze](https://www.tvmaze.com/api).

## Stack

- Java 25 + Spring Boot 3.5
- PostgreSQL 16
- Flyway (migrações)
- Spring Security + JWT
- Caffeine Cache
- Resilience4j (retry)
- SpringDoc / Swagger UI

## Pré-requisitos

- Docker e Docker Compose
- Java 25 (para execução local sem Docker)
- Maven 3.9+ (para execução local sem Docker)

---

## Execução com Docker (recomendado)

Sobe o banco e a aplicação juntos:

```bash
docker compose up --build
```

A aplicação estará disponível em `http://localhost:9012`.

Para parar:

```bash
docker compose down
```

---

## Execução local (sem Docker)

**1. Suba apenas o banco:**

```bash
docker compose up postgres -d
```

**2. Configure a datasource** — edite `src/main/resources/application-dev.yml` e ajuste a URL para apontar para `localhost`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/tvshow
```

**3. Execute a aplicação:**

```bash
./mvnw spring-boot:run
```

---

## Testes

```bash
# Testes unitários
./mvnw test

# Testes de integração
./mvnw verify
```

---

## Documentação (Swagger)

Após subir a aplicação, acesse:

```
http://localhost:9012/swagger-ui.html
```

---

## Variáveis de ambiente relevantes

| Variável                | Padrão  | Descrição                   |
|-------------------------|---------|-----------------------------|
| `SERVER_PORT`           | `9012`  | Porta da aplicação          |
| `ENVIRONMENT`           | `dev`   | Perfil Spring ativo         |
| `SPRING_PROFILES_ACTIVE`| `dev`   | Perfil (no Docker Compose)  |

> O secret JWT está definido em `application-dev.yml`. Em produção, substitua pela variável de ambiente correspondente.
