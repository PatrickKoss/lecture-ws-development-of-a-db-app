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

Follow these steps to build the complete student management API. Each step builds on the previous one, starting with simple transaction script pattern and evolving to clean architecture:

### Phase 1: Simple Transaction Script (Start Here)

- [ ] **1.1 Create Basic Student Model**
  - Create `model/Student.java` as simple POJO
  - Include: id (Long), firstName, lastName, email, registrationDate
  - Use basic getters/setters (no validation yet)
  - Keep it simple - just a data holder

  **Abstract Example (Book domain):**
  ```java
  // Abstract pattern: Simple POJO with basic fields
  public class Book {
      private Long id;
      private String title;
      private String author;
      private String isbn;
      private LocalDateTime publishedDate;
      
      // Default constructor
      public Book() {}
      
      // Full constructor
      public Book(Long id, String title, String author, String isbn, LocalDateTime publishedDate) {
          this.id = id;
          this.title = title;
          this.author = author;
          this.isbn = isbn;
          this.publishedDate = publishedDate;
      }
      
      // Standard getters and setters
      public Long getId() { return id; }
      public void setId(Long id) { this.id = id; }
      // ... etc for all fields
  }
  ```
  
  **Your Task:** Create similar `Student.java` with fields: id, firstName, lastName, email, registrationDate

- [ ] **1.2 Add Simple GET Endpoint**
  - Add `GET /api/students` endpoint to `StudentController`
  - Return hardcoded list of 2-3 students (no database yet)
  - Create `StudentResponse.java` DTO for JSON response
  - Test endpoint returns JSON properly

  **Abstract Example (Book domain):**
  ```java
  // Pattern: Response DTO that mirrors domain model
  public class BookResponse {
      private Long id;
      private String title;
      private String author;
      private String isbn;
      private LocalDateTime publishedDate;
      
      // Constructor from domain model
      public BookResponse(Book book) {
          this.id = book.getId();
          this.title = book.getTitle();
          this.author = book.getAuthor();
          this.isbn = book.getIsbn();
          this.publishedDate = book.getPublishedDate();
      }
      
      // Only getters (response DTOs are read-only)
      public Long getId() { return id; }
      // ... etc
  }
  
  // Pattern: Controller with hardcoded data
  @GetMapping
  public ResponseEntity<List<BookResponse>> getAllBooks() {
      List<Book> books = Arrays.asList(
          new Book(1L, "Clean Code", "Robert Martin", "978-0132350884", LocalDateTime.now().minusYears(1)),
          new Book(2L, "Spring in Action", "Craig Walls", "978-1617294945", LocalDateTime.now().minusMonths(6))
      );
      
      List<BookResponse> response = books.stream()
          .map(BookResponse::new)
          .collect(Collectors.toList());
          
      return ResponseEntity.ok(response);
  }
  ```
  
  **Your Task:** Create `StudentResponse.java` and implement `GET /api/students` endpoint with hardcoded students

  **Test:** Visit <http://localhost:8081/api/students> - should return JSON array

### Phase 2: Add Database Persistence

- [ ] **2.1 Add Database Migration**
  - Create `src/main/resources/db/migration/V1__Create_student_table.sql`
  - Define student table: id, first_name, last_name, email, registration_date
  - Test migration runs on startup

  **Abstract Example (Book domain):**
  ```sql
  -- Pattern: Flyway migration with table creation and test data
  CREATE TABLE book (
      id BIGINT PRIMARY KEY AUTOINCREMENT,
      title VARCHAR(100) NOT NULL,
      author VARCHAR(100) NOT NULL,
      isbn VARCHAR(20) NOT NULL,
      published_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  );
  
  -- Insert some test data for development
  INSERT INTO book (title, author, isbn, published_date) VALUES
  ('Clean Code', 'Robert Martin', '978-0132350884', '2008-08-01 10:00:00'),
  ('Spring in Action', 'Craig Walls', '978-1617294945', '2018-10-01 14:30:00');
  ```
  
  **Your Task:** Create `V1__Create_student_table.sql` with student fields and test data

- [ ] **2.2 Create JPA Entity and Repository**
  - Convert `Student.java` to JPA entity with `@Entity`, `@Table`
  - Add JPA annotations: `@Id`, `@GeneratedValue`, `@Column`
  - Create `StudentRepository.java` extending `JpaRepository<Student, Long>`
  - Inject repository into controller and return real database data

  **Abstract Example (Book domain):**
  ```java
  // Pattern: JPA Entity with proper annotations
  @Entity
  @Table(name = "book")
  public class Book {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      
      @Column(name = "title", nullable = false, length = 100)
      private String title;
      
      @Column(name = "author", nullable = false, length = 100)
      private String author;
      
      @Column(name = "isbn", nullable = false, length = 20)
      private String isbn;
      
      @Column(name = "published_date", nullable = false)
      private LocalDateTime publishedDate;
      
      // Keep existing constructors, getters, setters
  }
  ```

  ```java
  // Pattern: Repository interface
  @Repository
  public interface BookRepository extends JpaRepository<Book, Long> {
      // Spring Data JPA provides:
      // - List<Book> findAll()
      // - Optional<Book> findById(Long id)
      // - Book save(Book book)
      // - void deleteById(Long id)
      // - boolean existsById(Long id)
  }
  ```

  ```java
  // Pattern: Controller with injected repository
  @RestController
  @RequestMapping("/api/books")
  public class BookController {
      
      private final BookRepository bookRepository;
      
      // Constructor injection (recommended)
      public BookController(BookRepository bookRepository) {
          this.bookRepository = bookRepository;
      }
      
      @GetMapping
      public ResponseEntity<List<BookResponse>> getAllBooks() {
          List<Book> books = bookRepository.findAll(); // Real database query
          List<BookResponse> response = books.stream()
              .map(BookResponse::new)
              .collect(Collectors.toList());
          return ResponseEntity.ok(response);
      }
  }
  ```
  
  **Your Task:** Convert `Student.java` to JPA entity, create `StudentRepository.java`, and update controller to use database

- [ ] **2.3 Add CRUD Operations**
  - Add `POST /api/students` (create student)
  - Add `GET /api/students/{id}` (get single student)
  - Add `PUT /api/students/{id}` (update student)  
  - Add `DELETE /api/students/{id}` (delete student)
  - Create `CreateStudentRequest.java` and `UpdateStudentRequest.java` DTOs
  - Handle basic error cases (student not found)

  **Abstract Example (Book domain):**
  ```java
  // Pattern: Request DTO with conversion method
  public class CreateBookRequest {
      private String title;
      private String author;
      private String isbn;
      
      public CreateBookRequest() {}
      
      public Book toBook() {
          return new Book(null, title, author, isbn, LocalDateTime.now());
      }
      
      // getters/setters...
  }
  
  // Pattern: CRUD controller methods
  @PostMapping
  public ResponseEntity<BookResponse> createBook(@RequestBody CreateBookRequest request) {
      Book book = request.toBook();
      Book saved = bookRepository.save(book);
      return ResponseEntity.status(HttpStatus.CREATED).body(new BookResponse(saved));
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<BookResponse> getBook(@PathVariable Long id) {
      Book book = bookRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
      return ResponseEntity.ok(new BookResponse(book));
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @RequestBody UpdateBookRequest request) {
      Book book = bookRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
      
      // Update fields
      book.setTitle(request.getTitle());
      book.setAuthor(request.getAuthor());
      book.setIsbn(request.getIsbn());
      
      Book updated = bookRepository.save(book);
      return ResponseEntity.ok(new BookResponse(updated));
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
      if (!bookRepository.existsById(id)) {
          throw new ResourceNotFoundException("Book not found with id: " + id);
      }
      bookRepository.deleteById(id);
      return ResponseEntity.noContent().build();
  }
  ```

  **Your Task:** Implement all CRUD operations for students following the same pattern

  **Test:** Use Swagger UI at <http://localhost:8081/swagger-ui.html> to test all operations

### Phase 3: Add Input Validation and Business Logic

- [ ] **3.1 Add Input Validation**
  - Add validation annotations to request DTOs (`@NotNull`, `@Email`, `@Size`)
  - Add `@Valid` to controller methods
  - Test validation works and returns proper error messages
  - Update `GlobalExceptionHandler` for validation errors

  **Abstract Example (Book domain):**
  ```java
  // Pattern: Request DTO with validation annotations
  public class CreateBookRequest {
      @NotBlank(message = "Title is required")
      @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
      private String title;
      
      @NotBlank(message = "Author is required")
      @Size(min = 2, max = 100, message = "Author must be between 2 and 100 characters")
      private String author;
      
      @NotBlank(message = "ISBN is required")
      @Pattern(regexp = "^978-\\d{10}$", message = "ISBN must be in format 978-xxxxxxxxxx")
      private String isbn;
      
      // getters/setters...
  }
  
  // Pattern: Controller with @Valid
  @PostMapping
  public ResponseEntity<BookResponse> createBook(@Valid @RequestBody CreateBookRequest request) {
      // Spring automatically validates and returns 400 if invalid
      Book book = request.toBook();
      return ResponseEntity.status(HttpStatus.CREATED).body(new BookResponse(book));
  }
  ```
  
  **Your Task:** Add validation annotations to student request DTOs and use `@Valid` in controller

- [ ] **3.2 Add Business Rules**
  - Email must be unique (add database constraint + check)
  - Student names cannot be empty or only whitespace
  - Registration date defaults to current time if not provided
  - Add custom `StudentAlreadyExistsException` for duplicate emails

  **Abstract Example (Book domain):**
  ```java
  // Pattern: Custom business exception
  public class BookAlreadyExistsException extends RuntimeException {
      public BookAlreadyExistsException(String isbn) {
          super("Book with ISBN '" + isbn + "' already exists");
      }
  }
  
  // Pattern: Database constraint
  ALTER TABLE book ADD CONSTRAINT uk_book_isbn UNIQUE (isbn);
  
  // Pattern: Repository with business queries
  public interface BookRepository extends JpaRepository<Book, Long> {
      boolean existsByIsbn(String isbn);
      Optional<Book> findByIsbn(String isbn);
  }
  ```
  
  **Your Task:** Add uniqueness constraint for student email and create custom exception

- [ ] **3.3 Add Service Layer**
  - Create `StudentService.java` with `@Service` annotation
  - Move business logic from controller to service
  - Add `@Transactional` to service methods
  - Controller should only handle HTTP concerns, delegate to service

  **Abstract Example (Book domain):**
  ```java
  // Pattern: Service layer with business logic
  @Service
  @Transactional
  public class BookService {
      
      private final BookRepository bookRepository;
      
      public BookService(BookRepository bookRepository) {
          this.bookRepository = bookRepository;
      }
      
      @Transactional(readOnly = true)
      public List<Book> getAllBooks() {
          return bookRepository.findAll();
      }
      
      public Book createBook(CreateBookRequest request) {
          // Business rule: Check ISBN uniqueness
          if (bookRepository.existsByIsbn(request.getIsbn())) {
              throw new BookAlreadyExistsException(request.getIsbn());
          }
          
          // Business rule: Normalize data
          Book book = new Book(null, 
              request.getTitle().trim(), 
              request.getAuthor().trim(), 
              request.getIsbn().trim(), 
              LocalDateTime.now());
              
          return bookRepository.save(book);
      }
  }
  
  // Pattern: Thin controller delegating to service
  @RestController
  public class BookController {
      private final BookService bookService;
      
      @PostMapping
      public ResponseEntity<BookResponse> createBook(@Valid @RequestBody CreateBookRequest request) {
          Book book = bookService.createBook(request);
          return ResponseEntity.status(HttpStatus.CREATED).body(new BookResponse(book));
      }
  }
  ```
  
  **Your Task:** Create `StudentService.java` and move all business logic from controller to service
### Phase 4: Refactor to Hexagonal Architecture (Ports & Adapters)

- [ ] **4.1 Extract Domain Model**
  - Move `Student.java` to `domain/model/` package
  - Make it immutable with private constructor
  - Add static factory methods and validation in constructor
  - Create `withUpdatedInfo()` method for modifications
  - Move exceptions to `domain/exception/` package

  **Abstract Example (Book domain):**
  ```java
  // Pattern: Immutable domain model with business logic
  public class Book {
      private final Long id;
      private final String title;
      private final String author;
      private final String isbn;
      private final LocalDateTime publishedDate;
      
      private Book(Long id, String title, String author, String isbn, LocalDateTime publishedDate) {
          this.id = id;
          this.title = validateTitle(title);
          this.author = validateAuthor(author);
          this.isbn = validateIsbn(isbn);
          this.publishedDate = publishedDate != null ? publishedDate : LocalDateTime.now();
      }
      
      public static Book create(String title, String author, String isbn) {
          return new Book(null, title, author, isbn, LocalDateTime.now());
      }
      
      public static Book restore(Long id, String title, String author, String isbn, LocalDateTime publishedDate) {
          return new Book(id, title, author, isbn, publishedDate);
      }
      
      public Book withUpdatedInfo(String title, String author, String isbn) {
          return new Book(this.id, title, author, isbn, this.publishedDate);
      }
      
      private String validateTitle(String title) {
          if (title == null || title.trim().isEmpty()) {
              throw new IllegalArgumentException("Title cannot be empty");
          }
          return title.trim();
      }
      
      // Only getters (immutable)
      public Long getId() { return id; }
      // ... etc
  }
  ```

- [ ] **4.2 Define Ports (Interfaces)**
  - Create use case interfaces in `application/port/in/`
  - Create repository interface in `application/port/out/`

  **Abstract Example (Book domain):**
  ```java
  // Pattern: Use case interface with command objects
  public interface CreateBookUseCase {
      Book createBook(CreateBookCommand command);
      
      record CreateBookCommand(String title, String author, String isbn) {}
  }
  
  // Pattern: Repository port (interface)
  public interface BookPort {
      Book save(Book book);
      Optional<Book> findById(Long id);
      List<Book> findAll();
      boolean existsByIsbn(String isbn);
      void deleteById(Long id);
  }
  ```

- [ ] **4.3 Implement Adapters**
  - Move controller to `adapter/in/web/StudentController.java`
  - Create `adapter/out/persistence/StudentPersistenceAdapter.java`
  - Create domain service implementing use cases

  **Abstract Example (Book domain):**
  ```java
  // Pattern: Application service implementing use cases
  @Service
  @Transactional
  public class BookService implements CreateBookUseCase /* other use cases */ {
      
      private final BookPort bookPort;
      
      public BookService(BookPort bookPort) {
          this.bookPort = bookPort;
      }
      
      @Override
      public Book createBook(CreateBookCommand command) {
          if (bookPort.existsByIsbn(command.isbn())) {
              throw new BookAlreadyExistsException(command.isbn());
          }
          
          Book book = Book.create(command.title(), command.author(), command.isbn());
          return bookPort.save(book);
      }
  }
  
  // Pattern: Persistence adapter implementing port
  @Component
  public class BookPersistenceAdapter implements BookPort {
      private final BookJpaRepository jpaRepository;
      
      @Override
      public Book save(Book book) {
          BookJpaEntity entity = BookJpaEntity.fromDomain(book);
          BookJpaEntity saved = jpaRepository.save(entity);
          return saved.toDomain();
      }
  }
  ```

**Your Task:** Refactor student code to hexagonal architecture following these patterns

**Key Insight:** Domain model is now pure business logic with no framework dependencies!

### Phase 5-7: Advanced Features (Brief Abstract Examples)

**Your Task:** Follow the patterns below to implement logging, authentication, and testing for students

- [ ] **5.1 Add Request/Response Logging**
  **Pattern:** Create filter with correlation IDs and structured logging
  ```java
  // Pattern: Filter for request tracking
  @Component
  public class LoggingFilter implements Filter {
      @Override
      public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
          String correlationId = UUID.randomUUID().toString();
          MDC.put("correlationId", correlationId);
          log.info("Request: {} {}", method, uri);
          chain.doFilter(request, response);
          MDC.clear();
      }
  }
  ```

- [ ] **5.2 Add Custom Metrics**
  **Pattern:** Use Micrometer for business metrics and timing
  ```java
  // Pattern: Service with metrics
  public Book createBook(CreateBookCommand command) {
      Timer.Sample sample = Timer.start(meterRegistry);
      try {
          Book book = // business logic
          bookCreatedCounter.increment();
          return book;
      } finally {
          sample.stop(Timer.builder("book.create.duration").register(meterRegistry));
      }
  }
  ```

- [ ] **6.1 Add User Authentication**
  **Pattern:** User domain model with encrypted passwords
  ```java
  // Pattern: User domain with security
  public class User {
      public static User create(String username, String email, String rawPassword) {
          String passwordHash = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
          return new User(null, username, email, passwordHash, Set.of(Role.USER));
      }
  }
  ```

- [ ] **6.2 Add JWT Endpoints**
  **Pattern:** Auth controller with login and refresh
  ```java
  // Pattern: Authentication endpoints
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
      User user = authService.authenticate(request.email(), request.password());
      String accessToken = jwtService.generateToken(user);
      return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
  }
  ```

- [ ] **7.1 Add Unit Tests**
  **Pattern:** Test domain and application layers with mocks
  ```java
  // Pattern: Unit test with mocks
  @ExtendWith(MockitoExtension.class)
  class BookServiceTest {
      @Mock private BookPort bookPort;
      @InjectMocks private BookService bookService;
      
      @Test
      void createBook_ShouldCreateBook_WhenIsbnIsUnique() {
          // Given-When-Then pattern
          var command = new CreateBookCommand("Title", "Author", "ISBN");
          when(bookPort.existsByIsbn("ISBN")).thenReturn(false);
          
          Book result = bookService.createBook(command);
          
          verify(bookPort).save(any(Book.class));
      }
  }
  ```

- [ ] **7.2 Add Integration Tests**
  **Pattern:** End-to-end tests with real HTTP calls
  ```java
  // Pattern: Integration test
  @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
  class BookControllerIntegrationTest {
      @Autowired private TestRestTemplate restTemplate;
      
      @Test
      void createBook_ShouldReturn201_WhenValidRequest() {
          var request = new CreateBookRequest("Title", "Author", "ISBN");
          
          ResponseEntity<BookResponse> response = restTemplate.postForEntity(
              "/api/books", request, BookResponse.class);
              
          assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
      }
  }
  ```

## Architecture Evolution 🏗️

This exercise teaches architecture through **progressive refinement** - starting simple and evolving to clean architecture:

### Phase 1-3: Transaction Script Pattern
**Simple and pragmatic approach for getting started**

```
Controller → Service → Repository → Database
```

- **Controller**: Handles HTTP requests/responses
- **Service**: Contains business logic and validation  
- **Repository**: Data access layer (Spring Data JPA)
- **Model**: Simple POJOs with JPA annotations

**When to use:** Small applications, rapid prototyping, learning fundamentals

### Phase 4+: Hexagonal Architecture (Ports & Adapters)
**Clean architecture for maintainable, testable systems**

```
Adapters → Application → Domain
```

**Domain Layer** (`domain/`)
- Pure business logic, no external dependencies
- Immutable domain entities with business rules
- Domain exceptions
- No Spring annotations

**Application Layer** (`application/`)
- Use case orchestration
- Port interfaces (inbound and outbound)
- Application services implementing business workflows

**Adapter Layer** (`adapter/`)
- **Inbound**: REST controllers, DTOs, web concerns
- **Outbound**: JPA repositories, external APIs, infrastructure

### Key Benefits of Evolution

✅ **Learning Progression**: Start simple, add complexity gradually
✅ **Practical Understanding**: See why clean architecture matters
✅ **Refactoring Skills**: Learn to transform legacy code
✅ **Architecture Decision**: Understand when to apply which pattern

### Dependency Rule (Phase 4+)
Dependencies must point inward: `Adapters → Application → Domain`

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

### Phase 1-3: Fundamentals
- ✅ **Spring Boot 3** modern features and configuration
- ✅ **REST API Design** with proper HTTP semantics and documentation  
- ✅ **Database Integration** with JPA and Flyway migrations
- ✅ **Input Validation** and error handling patterns
- ✅ **Transaction Script** pattern for rapid development

### Phase 4: Clean Architecture  
- ✅ **Hexagonal Architecture** principles and implementation
- ✅ **Dependency Inversion** and ports & adapters pattern
- ✅ **Domain-Driven Design** basics with immutable entities
- ✅ **Refactoring** legacy code to clean architecture
- ✅ **SOLID Principles** in practice

### Phase 5-6: Production Ready
- ✅ **Observability** with logging, metrics, and monitoring
- ✅ **Security** with JWT authentication and authorization
- ✅ **Performance** monitoring and optimization
- ✅ **Error Handling** and proper HTTP status codes

### Phase 7: Quality Assurance
- ✅ **Test-Driven Development** with proper test pyramid
- ✅ **Unit Testing** with mocks and isolation
- ✅ **Integration Testing** with real databases
- ✅ **Code Quality** and best practices

---

**Start with Phase 1** and work through each todo systematically. Each phase builds important skills and prepares you for the next level of complexity.

**Key Learning:** See how simple solutions evolve into clean, maintainable architectures! 🚀
