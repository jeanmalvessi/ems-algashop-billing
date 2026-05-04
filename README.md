# billing

Microservice responsible for invoice generation, credit card management, and payment processing in the [AlgaShop](https://github.com/jeanmalvessi/ems-algashop-meta) platform.

Built using a **pragmatic DDD** approach, allowing domain models to be closer to persistence entities to reduce implementation complexity while still benefiting from DDD principles.

## Responsibilities

- Invoice creation and lifecycle management (ISSUED → PAID / CANCELED)
- Credit card registration and tokenization via FastPay gateway
- Payment processing through the FastPay payment API
- Receiving asynchronous payment status updates via webhooks
- Audit tracking on domain entities (created_at, created_by, last_modified)

## Architecture

- **Domain Layer:** Aggregates (`Invoice`, `CreditCard`), Value Objects, Domain Events, Repository interfaces
- **Application Layer:** Use-case services (`InvoiceManagementApplicationService`, `CreditCardManagementApplicationService`) and query services
- **Infrastructure Layer:** JPA repositories, FastPay REST client, webhook receiver, ModelMapper-based DTO mapping

## Tech Stack

- **Java 25**, Spring Boot 4.0.1
- **Spring Data JPA** + PostgreSQL 17 (persistence)
- **Flyway** (database migrations)
- **Spring REST Client** (FastPay payment gateway integration)
- **Spring Cloud Circuit Breaker** (resilience and retries)
- **Spring Boot Actuator** (monitoring and health checks)
- **WireMock** (mocks for FastPay API in tests)
- **Testcontainers** (PostgreSQL integration tests)
- **ModelMapper** (entity/DTO mapping)
- **Commons Validator** (input validation)
- **Lombok**

## API

Base path: `/api/v1`

| Method | Path | Description |
|--------|------|-------------|
| POST | `/invoices` | Generate invoice |
| GET | `/invoices/{invoiceId}` | Get invoice by ID |
| GET | `/invoices` | List/filter invoices |
| POST | `/customers/{customerId}/credit-cards` | Register credit card |
| GET | `/customers/{customerId}/credit-cards` | List customer credit cards |
| GET | `/customers/{customerId}/credit-cards/{cardId}` | Get credit card |
| DELETE | `/customers/{customerId}/credit-cards/{cardId}` | Delete credit card |

## Running

```bash
./gradlew bootRun
```

Default port: **8082** (development profile)

Database: PostgreSQL `billing` database on `localhost:5432`
Payment gateway: FastPay on `localhost:9995` (mock)
