**!! Test are WIP !!** Integration tests are currently not working (connection between Testcontainers & WireMock is not set up properly)

# Eligibility Evaluation Service

This is a backend service responsible for evaluating user eligibility and logging audit information for all incoming and outgoing API interactions. It is designed with simplicity, observability, and testability in mind.

## 🔧 Technologies Used

- **Kotlin** – Modern and concise JVM language
- **Spring Boot** – Application framework for easy setup and configuration
- **Spring Web & WebFlux** – For synchronous REST endpoints and reactive client calls
- **Spring Data JPA** – ORM support with PostgreSQL
- **Spring Actuator** – Monitoring, health checks, and HTTP tracing
- **Gradle** – Build tool
- **Testcontainers** – For integration testing with real Postgres
- **WireMock** – API client mocking in tests
- **Docker / Podman** – Containerization support

## 🏗️ Architecture Overview

- Domain-driven structure
- Clear separation between DTOs, domain models, and persistence entities
- Manual mappers for clarity and flexibility
- API clients with pluggable mocks via Spring profiles
- Centralized exception and error handling
- Audit logging to database with optional HTTP trace integration

## 🧪 Testing

- **Unit tests** for domain logic
- **Integration tests** using Testcontainers (PostgreSQL) and WireMock

## 🚀 Running the Project

Make sure you have Docker/Podman running for database support.

```bash
./gradlew bootRun
