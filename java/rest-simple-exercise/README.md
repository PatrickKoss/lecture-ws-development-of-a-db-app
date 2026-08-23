# REST API mit Spring Boot

## Wo stehen wir auf der roten Linie

An Tag 3 baut ihr mit den Decks 10 bis 12 aus dem HTTP-Vertrag eine laufende REST API und ergänzt klare Grenzen, Fehlerbehandlung und Tests. Davor habt ihr mit JDBC auf die Datenbank zugegriffen und den Datenzugriff im Repository Pattern gekapselt.

`Student` ist das durchgängige Beispiel aus den Folien. In der Übung arbeitet ihr nicht die Student-API nach. Öffnet `exercises/<domain>/06-rest.md` und übertragt jeden Schritt auf die dort genannte Hauptressource eurer Domäne.

## Setup und Start

Ihr braucht Java 21. Gradle müsst ihr nicht installieren, der Wrapper liegt im Projekt. `build.gradle.kts` enthält Spring Web, Spring Data JPA, Validation, Actuator, Springdoc OpenAPI, SQLite, Flyway, JUnit und Mockito.

Startet die Anwendung aus diesem Verzeichnis:

```bash
./gradlew bootRun
```

Ohne gesetzte Umgebungsvariable `SERVER_PORT` läuft die Anwendung auf Port `8081`. Das steht in `src/main/resources/application.properties`.

- Health-Endpoint: <http://localhost:8081/api/students/health>
- Swagger UI: <http://localhost:8081/swagger-ui.html>
- OpenAPI JSON: <http://localhost:8081/v3/api-docs>

Der Security-Starter ist in `build.gradle.kts` auskommentiert. Swagger UI und eure Endpunkte sind ohne Anmeldung erreichbar. Wie eine Security Filter Chain aussieht, zeigt Deck 12 am Beispiel `../rest-simple`.

Prüft vor jeder Abgabe den Build:

```bash
./gradlew build
```

## Phase 1: Health, erster GET und feste Daten

Folien: [Deck 10](../../slides/decks/10-spring-boot.html), Folien 10.6 bis 10.11. Übung: `exercises/<domain>/06-rest.md`, Übung 9.

Der vorhandene `StudentController` liefert bereits `GET /api/students/health`. Startet die Anwendung und prüft zuerst diesen Endpoint. Ergänzt danach `GET /api/students` mit zwei festen Datensätzen. In dieser Phase gibt es noch kein Repository und keinen Datenbankzugriff.

Für das Beispiel hat `Student` diese Felder:

```text
id: Long
firstName: String
lastName: String
email: String
studentNumber: String
enrollmentDate: LocalDate
```

Ein Response-DTO legt fest, welche Felder die API ausgibt:

```java
package com.example.restsimple.dto;

import java.time.LocalDate;

public record StudentResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String studentNumber,
        LocalDate enrollmentDate
) {}
```

Ergänzt im vorhandenen Controller zunächst diese Methode:

```java
@GetMapping
public ResponseEntity<List<StudentResponse>> getAllStudents() {
    var students = List.of(
        new StudentResponse(1L, "Mina", "Özdemir", "mina@example.org", "S-1001", LocalDate.of(2025, 10, 1)),
        new StudentResponse(2L, "Noah", "Becker", "noah@example.org", "S-1002", LocalDate.of(2025, 10, 1))
    );
    return ResponseEntity.ok(students);
}
```

Öffnet anschließend `GET /api/students` in Swagger UI. Erwartet werden Status `200` und ein JSON-Array mit zwei Einträgen. Nutzt in eurem Projekt die Felder und den Pfad aus eurer Domänenkarte.

## Phase 2: JPA, Repository und erster POST

Folien: [Deck 10](../../slides/decks/10-spring-boot.html), Folien 10.12 bis 10.18. Übung: `exercises/<domain>/06-rest.md`, Übung 9.

Legt `src/main/resources/db/migration/V1__Create_students_table.sql` an. Die Beispielmigration verwendet die Namen aus den Folien:

```sql
CREATE TABLE students (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    student_number VARCHAR(30) NOT NULL UNIQUE,
    enrollment_date DATE NOT NULL
);
```

Die JPA-Entity bildet dieselben sechs Felder ab. Legt sie zum Beispiel unter `model/Student.java` an:

```java
package com.example.restsimple.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "student_number", nullable = false, unique = true, length = 30)
    private String studentNumber;
    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;
    protected Student() {}
    public Student(Long id, String firstName, String lastName, String email,
                   String studentNumber, LocalDate enrollmentDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.studentNumber = studentNumber;
        this.enrollmentDate = enrollmentDate;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getStudentNumber() { return studentNumber; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
}
```

Spring Data erzeugt die Implementierung dieses Repository-Interfaces:

```java
package com.example.restsimple.repository;

import com.example.restsimple.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
    boolean existsByStudentNumber(String studentNumber);
}
```

Für den ersten POST braucht ihr ein Request-DTO. Der Request enthält keine `id`, weil die Datenbank sie erzeugt. Legt direkt diese endgültige Form an. Die Annotationen werden in Phase 3 mit `@Valid` aktiv.

```java
package com.example.restsimple.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CreateStudentRequest(
        @NotBlank @Size(max = 100) String firstName,
        @NotBlank @Size(max = 100) String lastName,
        @NotBlank @Email String email,
        @NotBlank @Size(max = 30) String studentNumber,
        @NotNull @PastOrPresent LocalDate enrollmentDate
) {}
```

Injiziert `StudentRepository` über den Konstruktor in den Controller. Ersetzt den festen GET und ergänzt den POST:

```java
@GetMapping
public ResponseEntity<List<StudentResponse>> getAllStudents() {
    var response = studentRepository.findAll().stream()
        .map(s -> new StudentResponse(s.getId(), s.getFirstName(), s.getLastName(),
            s.getEmail(), s.getStudentNumber(), s.getEnrollmentDate()))
        .toList();
    return ResponseEntity.ok(response);
}

@PostMapping
public ResponseEntity<StudentResponse> createStudent(@RequestBody CreateStudentRequest request) {
    var student = new Student(null, request.firstName(), request.lastName(), request.email(),
        request.studentNumber(), request.enrollmentDate());
    var saved = studentRepository.save(student);
    var response = new StudentResponse(saved.getId(), saved.getFirstName(), saved.getLastName(),
        saved.getEmail(), saved.getStudentNumber(), saved.getEnrollmentDate());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
```

Prüft in Swagger UI zuerst `POST` mit Status `201` und danach `GET` mit Status `200`. Der angelegte Datensatz muss im zweiten Response stehen.

## Phase 3: DTOs, Validation und Fehler

Folien: [Deck 11](../../slides/decks/11-good-design.html), Folien 11.3 bis 11.14 und 11.17. Übung: `exercises/<domain>/06-rest.md`, Übung 10.

Vervollständigt das CRUD für eure Hauptressource. Request-DTOs beschreiben erlaubte Eingaben, Response-DTOs den öffentlichen Vertrag. Gebt nie eine JPA-Entity direkt zurück. Aktiviert jetzt die vorbereitete Bean Validation am HTTP-Rand:

```java
public ResponseEntity<StudentResponse> createStudent(
        @Valid @RequestBody CreateStudentRequest request) {
    // Call repository or service
}
```

Die Klassen `ErrorResponse`, `ResourceNotFoundException` und `GlobalExceptionHandler` liegen bereits im Skeleton. `ErrorResponse` hat die Felder `message` und `details`:

```java
public class ErrorResponse {
    private String message;
    private String details;
    public ErrorResponse(String message) {
        this.message = message;
    }
    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }
    public String getMessage() { return message; }
    public String getDetails() { return details; }
}
```

Erweitert das vorhandene `@ControllerAdvice` um die Fehlerfälle eurer Domänenkarte. Dieses Muster übersetzt Validation, fehlende Ressourcen und Konflikte in HTTP-Responses:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    ResponseEntity<ErrorResponse> handleConflict(StudentAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse(ex.getMessage(), "STUDENT_ALREADY_EXISTS"));
    }
}
```

Ein Service lohnt sich, sobald ein Use Case eine Fachregel prüft, mehrere Repositories koordiniert oder eine Transaktionsgrenze braucht. Eure Domänenkarte enthält solche Regeln. Verschiebt die betroffenen Use Cases deshalb in einen mit `@Service` markierten Typ und setzt die Transaktionsgrenze mit `@Transactional`. Ein einfaches `findAll()` ohne weitere Entscheidung darf direkt das Repository aufrufen.

Setzt die Statuscodes aus eurer Domänenkarte um. Typisch sind `400` für ungültige Requests, `404` für fehlende Ressourcen, `409` für einen Konflikt, `201` nach dem Anlegen und `204` nach dem Löschen. Baut danach den dort genannten Beziehungsendpunkt.

## Phase 4: Hexagonale Architektur ansehen

Folien: [Deck 11](../../slides/decks/11-good-design.html), Folien 11.15 bis 11.16. Übung: keine Aufgabe.

Phase 4 ist keine Aufgabe für euer Projekt. Das vollständige Beispiel unter [`../rest-simple`](../rest-simple) trennt den Anwendungskern von REST und JPA. Interfaces bilden dort Ports, Controller und Persistenzklassen sind Adapter. Öffnet die Package-Struktur und verfolgt einen POST durch `adapter/in`, `application` und `adapter/out`. Für eure Abgabe reichen die Schichten aus Phase 3.

## Phase 5: Zwei gezielte Tests

Folien: [Deck 12](../../slides/decks/12-making-it-solid.html), Folien 12.3 bis 12.8 und 12.17. Übung: `exercises/<domain>/06-rest.md`, Übung 11, optional.

Schreibt genau die zwei HTTP-Tests aus eurer Domänenkarte. MockMvc prüft Mapping, Validation, Status und JSON am HTTP-Rand. Mockito ersetzt Repository oder Service, wenn der Test keine echte Datenbank braucht.

Ein POST-Test folgt diesem Muster:

```java
mockMvc.perform(post("/api/students")
        .contentType(MediaType.APPLICATION_JSON)
        .content(validStudentJson))
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.id").isNumber())
    .andExpect(jsonPath("$.studentNumber").value("S-1003"));
```

Testet keine Getter und keine privaten Methoden. Die zwei Fälle aus `exercises/<domain>/06-rest.md` sind verbindlich, wenn ihr Übung 11 bearbeitet.

## Phase 6: Correlation ID und Actuator

Folien: [Deck 12](../../slides/decks/12-making-it-solid.html), Folien 12.9 bis 12.11 und 12.17. Übung: `exercises/<domain>/06-rest.md`, Übung 11, optional.

Ein Filter übernimmt `X-Correlation-ID` oder erzeugt eine UUID. Er schreibt dieselbe ID in MDC und in den Response. Das `finally` räumt MDC nach jedem Request auf.

```java
@Component
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String id = Optional.ofNullable(request.getHeader("X-Correlation-ID"))
            .filter(value -> !value.isBlank())
            .orElseGet(() -> UUID.randomUUID().toString());
        try {
            MDC.put("correlationId", id);
            response.setHeader("X-Correlation-ID", id);
            chain.doFilter(request, response);
        } finally {
            MDC.remove("correlationId");
        }
    }
}
```

Actuator ist bereits konfiguriert. Prüft `/actuator/health`, `/actuator/info`, `/actuator/metrics` und `/actuator/prometheus`. Gebt solche Endpoints in einer produktiven Anwendung nur bewusst frei und schützt sensible Informationen.

## Phase 7: CORS und Docker einordnen

Folien: [Deck 12](../../slides/decks/12-making-it-solid.html), Folien 12.12 bis 12.14. Übung: `exercises/<domain>/06-rest.md`, Übung 11, optional.

CORS ist eine Browserregel. Falls ein Frontend auf `http://localhost:3000` läuft, könnt ihr diese Origin gezielt erlauben:

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
```

CORS ersetzt keine Authentifizierung. Der vorhandene `Dockerfile` baut die VS-Code-Lernumgebung und kein Image für die Spring-Anwendung. Deck 12 zeigt deshalb nur, welche Teile ein späteres Anwendungsimage bräuchte: das Spring-JAR, eine Java Runtime, Port `8081` und ein Volume für `students.db`.

## Abgabe für Tag 3

Gebt das ausführbare Spring-Projekt für eure eigene Domäne ab. Dazu gehören die Endpoint-Liste mit Methoden und Statuscodes sowie ein Screenshot der eigenen API in Swagger UI. Falls ihr Übung 11 bearbeitet habt, kommen genau zwei grüne Tests und der passende `X-Correlation-ID`-Header mit Logeintrag hinzu.
