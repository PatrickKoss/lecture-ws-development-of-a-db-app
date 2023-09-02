# Introduction

This is the java implementation of the simple rest api. It was created using the spring cli.

```bash
spring init --build=gradle --java-version=17 --dependencies=web,data-jpa,h2,flyway rest-simple --type=gradle-project-kotlin
```

# Getting Started

Run the application:

```bash
make run
```

Run the test suite:

```bash
make test
```

Visit the swagger ui:

```bash
http://localhost:8081/swagger-ui/
```

# Implementation

1. Define the attributes of the Student data model, alongside its creation and update functionalities.
   Incorporate these structural definitions within the
   [model](src/main/java/com/example/restsimple/model/Student.java) sub-directory.
2. Formulate a database migration script based on the previously defined Student model. Insert this migration script in
   [migration](src/main/resources/db/migration/V1__Create_student_table.sql)
3. Implement a data repository layer by actualizing the requisite interface within
   the [repository](src/main/java/com/example/restsimple/repository/StudentRepository.java) sub-directory.
   It should support a deleteById and updateById operation in addition to the default implementations.
4. Architect a service layer to encapsulate business logic. This service should validate input data,
   construct Student objects, and interact with the data repository layer. Implement this interface
   within [service](src/main/java/com/example/restsimple/service/StudentServiceImpl.java).
5. Validate the service layer by conducting
   [tests](src/test/java/com/example/restsimple/service/StudentServiceImplTest.java).
6. Develop an HTTP handler for the REST API. This handler should support CRUD operations including:
   - Create a new student (POST /students)
   - Retrieve all students (GET /students)
   - Update a student by id (PUT /students/{id})
   - Delete a student by id (DELETE /students/{id})
The handler is responsible for deserializing the request payload, parsing URL path variables, and interfacing with the
service layer. Additionally, the handler should map service layer errors to appropriate HTTP status codes.
Integrate this handler within [controller](src/main/java/com/example/restsimple/controller).

7. Validate the controller layer by conducting
   [tests](src/test/java/com/example/restsimple/controller/StudentControllerTest.java).
8. Auto-generate Swagger API documentation, adhering to the given example code snippet for documentation annotations:

```java
    @GetMapping("/students")
@Operation(responses = {
        @ApiResponse(responseCode = "200", description = "Successfully got all students",
                content = @Content(schema = @Schema(implementation = StudentListResponse.class))),
})
public StudentListResponse getAllStudents() {}
```
