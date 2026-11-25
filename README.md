# FinTrack API

API REST de controle financeiro pessoal. O usuário cadastra contas, categorias e transações, define orçamentos mensais, anexa comprovantes e vê relatórios.

> **Status:** em fase inicial. O repositório tem o esqueleto da aplicação Spring Boot e o `compose.yml`. As funcionalidades, o modelo de dados e os endpoints descritos abaixo ainda **não foram implementados**.

## Stack

| Camada | Tecnologia | Situação |
| --- | --- | --- |
| Linguagem e framework | Java 21 + Spring Boot 4.2.0 (Web, Data JPA, Validation, Security) | Dependências declaradas |
| Banco | PostgreSQL 16 | Configurado no Compose |
| Migrations | Flyway | Dependência declarada, sem migrations |
| Containers | Docker + Docker Compose | Configurado |
| Testes | JUnit 5 + Testcontainers | Só o teste inicial em JUnit 5; Testcontainers planejado |
| Documentação da API | springdoc-openapi (Swagger UI) | Planejado |
| Nuvem | AWS: IAM, EC2, RDS, S3, CloudWatch, Lambda, SQS | Planejado |
| CI/CD | GitHub Actions | Planejado |

## Como executar

### Pré-requisitos

- Docker com Docker Compose
- JDK 21 (apenas para rodar fora do Docker)

### Com Docker Compose

Crie o arquivo `api/.env` (não versionado) com `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD`, `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD`. Depois, na raiz do projeto:

```sh
docker compose up --build
```

### Testes

Dentro de `api/`:

```sh
./mvnw test
```

O teste atual sobe o contexto completo do Spring, então precisa de um PostgreSQL acessível e das variáveis de ambiente configuradas.

## Modelo de dados (versão inicial, planejado)

| Tabela | Colunas principais | Relacionamentos |
| --- | --- | --- |
| `usuario` | id, nome, email (único), senha_hash, criado_em | 1:N com `conta`, `categoria`, `orcamento` |
| `conta` | id, usuario_id, nome, tipo (`CORRENTE`, `POUPANCA`, `CARTAO`), saldo | N:1 `usuario`; 1:N `transacao` |
| `categoria` | id, usuario_id, nome, tipo (`RECEITA`, `DESPESA`) | N:1 `usuario`; 1:N `transacao` |
| `transacao` | id, conta_id, categoria_id, valor `NUMERIC(12,2)`, data, descricao, comprovante_key | N:1 `conta`; N:1 `categoria` |
| `orcamento` | id, usuario_id, categoria_id, mes (`DATE`), limite | Único por (usuario, categoria, mes) |
| `transferencia` | id, conta_origem_id, conta_destino_id, valor, data | Duas FKs para `conta` |

Valores monetários usam `NUMERIC` no banco e `BigDecimal` no Java, nunca `float` ou `double`.

## Endpoints principais (planejados)

| Método e rota | Descrição |
| --- | --- |
| `POST /auth/registro` | Cadastro de usuário |
| `POST /auth/login` | Login com JWT |
| CRUD em `/contas`, `/categorias`, `/transacoes`, `/orcamentos` | Cadastros principais |
| `GET /transacoes?inicio=&fim=&categoria=&page=` | Listagem com filtros e paginação |
| `POST /transferencias` | Debita uma conta e credita outra em uma única transação de banco |
| `GET /relatorios/mensal?mes=2026-12` | Total por categoria, receitas vs. despesas e % do orçamento usado |
| `POST /transacoes/{id}/comprovante` | Upload do comprovante para o S3 |
| `POST /importacoes` | Importação de extrato CSV, processada de forma assíncrona |
