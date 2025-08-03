# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot REST API demonstrating **Hexagonal Architecture (Ports & Adapters)** for a simple student management system. The application is built with Java 21, Spring Boot 3.4.5, and uses SQLite for persistence with Flyway migrations.

## Build System & Development Commands

### Essential Commands

```bash
# Run the application (builds first)
make run

# Build the project
make build

# Run tests
make test

# Direct Gradle commands
./gradlew bootRun    # Run application directly
./gradlew test       # Run test suite
./gradlew build      # Build and test
```

### Application Access

- **Main Application**: http://localhost:8081
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **OpenAPI Docs**: http://localhost:8081/v3/api-docs

## Architecture

The codebase strictly follows **Hexagonal Architecture**:

### Core Structure

```
com.example.restsimple/
├── domain/           # Business logic & entities (no external dependencies)
│   ├── model/        # Domain entities (Student)
│   └── exception/    # Domain exceptions
├── application/      # Use cases & orchestration
│   ├── port/in/      # Inbound ports (use case interfaces)
│   ├── port/out/     # Outbound ports (repository interfaces)
│   └── service/      # Use case implementations
└── adapter/          # External world adapters
    ├── in/web/       # REST controllers & DTOs
    └── out/persistence/ # JPA repositories & entities
```

### Key Principles Applied

- **Domain Layer**: Pure business logic, no Spring annotations
- **Application Layer**: Use case orchestration with `@Service` and `@Transactional`
- **Adapters**: Handle external concerns (REST, JPA, etc.)
- **Dependency Rule**: Dependencies point inward toward domain

## Domain Model

### Student Entity

- Immutable domain entity with value object semantics
- Business rules enforced in constructor (null checks, length validation)
- Uses `withUpdatedInfo()` method for modifications (returns new instance)
- ID generation handled in application service, not domain

## Testing Strategy

### Current Test Structure

- **Unit Tests**: Service layer with Mockito mocks for ports
- **Framework**: JUnit 5 + Mockito
- **Pattern**: Given-When-Then structure
- **Coverage**: Full service layer coverage with edge cases

### Test Execution

```bash
./gradlew test                    # Run all tests
./gradlew test --tests "*Service*" # Run service tests only
```

## Database & Persistence

### Configuration

- **Database**: SQLite (file: `students.db`)
- **Migration**: Flyway (`src/main/resources/db/migration/`)
- **ORM**: Spring Data JPA with Hibernate
- **Dialect**: `org.hibernate.community.dialect.SQLiteDialect`

### Adding New Migrations

1. Create `V{version}__Description.sql` in `src/main/resources/db/migration/`
2. Use incremental version numbers (V2, V3, etc.)
3. Flyway runs migrations automatically on startup

## API Documentation

The application uses SpringDoc OpenAPI 3 for API documentation:

- Auto-generated from controller annotations
- Available at `/swagger-ui.html`
- JSON spec at `/v3/api-docs`

## Development Guidelines

### Adding New Features

1. Start with domain model changes (if needed)
2. Define ports (interfaces) in `application/port/`
3. Implement use cases in `application/service/`
4. Create adapters in `adapter/in/` or `adapter/out/`
5. Add comprehensive unit tests

### Code Style

- Follow existing package structure strictly
- Use record classes for DTOs and commands
- Keep controllers thin - delegate to use cases
- Domain entities should be immutable
- Use constructor injection for dependencies

### Error Handling

- Domain exceptions in `domain/exception/`
- Global exception handler in `exception/GlobalExceptionHandler.java`
- Custom `ErrorResponse` for consistent API responses
