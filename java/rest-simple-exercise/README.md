# REST Simple Exercise

This is a hands-on learning exercise to build a REST API for student management using **Spring Boot 3.4.5** with **Hexagonal Architecture (Ports & Adapters)**.

The project starts with a minimal setup including basic dependencies, configuration, and a health endpoint. You'll iteratively add features following clean architecture principles.

## Initial Setup

### Prerequisites

- Java 21
- Gradle (included via wrapper)

### Quick Start

```bash
# Run the application
make run

# Build the project
make build

# Run tests
make test

# Clean build artifacts
make clean
```

### Application URLs

- **Main Application**: http://localhost:8081
- **Health Endpoint**: http://localhost:8081/api/students/health
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **OpenAPI Docs**: http://localhost:8081/v3/api-docs
- **Actuator Health**: http://localhost:8081/actuator/health
- **Actuator Metrics**: http://localhost:8081/actuator/metrics

## Current Features ✅

The exercise starts with these essential components already configured:

- ✅ **Build Setup**: Spring Boot 3.4.5 with Java 21
- ✅ **Database**: SQLite with Hibernate Community Dialects
- ✅ **REST API**: Basic controller with health endpoint
- ✅ **Exception Handling**: Global exception handler with proper HTTP status codes
- ✅ **OpenAPI Documentation**: Swagger UI integration
- ✅ **Logging**: Comprehensive logging setup with file and console output
- ✅ **Monitoring**: Spring Actuator endpoints for health and metrics
- ✅ **Testing**: JUnit 5 and Mockito setup

## Exercise Todos 🎯

Follow these steps to build the complete student management API. Each step builds on the previous one:

### Phase 1: Domain Foundation

- [ ] **1.1 Create Student Domain Model**

  - Create `domain/model/Student.java` with immutable design
  - Include: id (UUID), firstName, lastName, email, registrationDate
  - Add validation rules in constructor (null checks, email format)
  - Implement `withUpdatedInfo()` method for modifications

- [ ] **1.2 Create Domain Exceptions**
  - Create `domain/exception/StudentNotFoundException.java`
  - Create `domain/exception/InvalidStudentDataException.java`
  - Update `GlobalExceptionHandler` to handle these new exceptions

### Phase 2: Application Layer (Use Cases)

- [ ] **2.1 Define Inbound Ports (Use Case Interfaces)**

  - Create `application/port/in/CreateStudentUseCase.java`
  - Create `application/port/in/GetStudentUseCase.java`
  - Create `application/port/in/UpdateStudentUseCase.java`
  - Create `application/port/in/DeleteStudentUseCase.java`
  - Define command objects for each use case

- [ ] **2.2 Define Outbound Ports (Repository Interfaces)**

  - Create `application/port/out/SaveStudentPort.java`
  - Create `application/port/out/LoadStudentPort.java`
  - Create `application/port/out/DeleteStudentPort.java`
  - Keep interfaces focused and single-purpose

- [ ] **2.3 Implement Application Services**
  - Create `application/service/StudentService.java`
  - Implement all use case interfaces
  - Use `@Service` and `@Transactional` annotations
  - Handle business logic and coordinate between ports

### Phase 3: Persistence Layer (Outbound Adapters)

- [ ] **3.1 Create JPA Entities**

  - Create `adapter/out/dto/StudentJpaEntity.java`
  - Map to `student` table with proper JPA annotations
  - Keep separate from domain model

- [ ] **3.2 Create JPA Repository**

  - Create `adapter/out/persistence/StudentJpaRepository.java` (extends JpaRepository)
  - Create `adapter/out/persistence/StudentPersistenceAdapter.java`
  - Implement outbound ports
  - Handle entity/domain model conversion

- [ ] **3.3 Add Database Migration**
  - Create `src/main/resources/db/migration/V1__Create_student_table.sql`
  - Define table schema with proper constraints
  - Test migration works on startup

### Phase 4: REST API Layer (Inbound Adapters)

- [ ] **4.1 Create DTOs**

  - Create `adapter/in/dto/CreateStudentRequest.java`
  - Create `adapter/in/dto/UpdateStudentRequest.java`
  - Create `adapter/in/dto/StudentResponse.java`
  - Add validation annotations (`@NotNull`, `@Email`, etc.)

- [ ] **4.2 Expand REST Controller**
  - Add CRUD endpoints to `StudentController`:
    - `POST /api/students` - Create student
    - `GET /api/students/{id}` - Get student by ID
    - `GET /api/students` - List all students
    - `PUT /api/students/{id}` - Update student
    - `DELETE /api/students/{id}` - Delete student
  - Add proper OpenAPI annotations
  - Handle validation and error responses

### Phase 5: Testing

- [ ] **5.1 Unit Tests - Domain Layer**

  - Test `Student` domain model validation rules
  - Test domain exceptions

- [ ] **5.2 Unit Tests - Application Layer**

  - Test `StudentService` with mocked ports
  - Use `@ExtendWith(MockitoExtension.class)`
  - Test happy path and error scenarios

- [ ] **5.3 Integration Tests - REST Layer**

  - Test `StudentController` endpoints
  - Use `@SpringBootTest` with test database
  - Test full request/response cycle

- [ ] **5.4 Integration Tests - Persistence Layer**
  - Test `StudentPersistenceAdapter`
  - Use `@DataJpaTest` for repository testing
  - Test entity mapping and queries

### Phase 6: Advanced Features (Optional)

- [ ] **6.1 Add Pagination**

  - Modify list endpoint to support pagination
  - Use Spring's `Pageable` and `Page<T>`

- [ ] **6.2 Add Search/Filtering**

  - Add query parameters for filtering students
  - Implement search by name, email, etc.

- [ ] **6.3 Add Validation Groups**

  - Use different validation rules for create vs update
  - Implement custom validators

- [ ] **6.4 Add Audit Fields**
  - Add createdAt, updatedAt, createdBy fields
  - Use JPA auditing features

## Architecture Principles

This exercise follows **Hexagonal Architecture** principles:

### Dependency Rule

Dependencies must point inward: Adapters → Application → Domain

### Layer Responsibilities

**Domain Layer** (`domain/`)

- Pure business logic, no external dependencies
- Domain entities with business rules
- Domain exceptions
- No Spring annotations

**Application Layer** (`application/`)

- Use case orchestration
- Port interfaces (inbound and outbound)
- Application services with `@Service` and `@Transactional`

**Adapter Layer** (`adapter/`)

- **Inbound**: REST controllers, DTOs
- **Outbound**: JPA repositories, external APIs
- Framework-specific code lives here

### Key Benefits

✅ **Testability**: Easy to unit test with mocked dependencies
✅ **Flexibility**: Can swap adapters without changing business logic  
✅ **Separation of Concerns**: Clear boundaries between layers
✅ **Independence**: Domain logic doesn't depend on frameworks

## Development Guidelines

### Code Style

- Follow existing package structure strictly
- Use record classes for DTOs and commands
- Keep controllers thin - delegate to use cases
- Domain entities should be immutable
- Constructor injection for dependencies

### Testing Strategy

- **Unit Tests**: Fast, isolated, mocked dependencies
- **Integration Tests**: Real database, full Spring context
- **Given-When-Then** structure for test readability

### Error Handling

- Custom exceptions in domain layer
- Global exception handler for consistent API responses
- Proper HTTP status codes

## Validation

As you implement each phase, validate by:

1. **Building and running tests**: `make test`
2. **Starting application**: `make run`
3. **Testing endpoints**: Use Swagger UI or curl
4. **Checking logs**: Verify proper logging output
5. **Architecture compliance**: Ensure dependency rule is followed

## Learning Objectives

After completing this exercise, you will understand:

- ✅ **Hexagonal Architecture** principles and implementation
- ✅ **Spring Boot 3** modern features and configuration
- ✅ **Clean Code** practices and SOLID principles
- ✅ **Test-Driven Development** with proper test pyramid
- ✅ **API Design** with proper HTTP semantics and documentation
- ✅ **Database Integration** with JPA and migrations
- ✅ **Error Handling** and validation patterns

---

**Start with Phase 1** and work through each todo systematically. Take time to understand the architecture decisions and test your implementation at each step.

Happy coding! 🚀
