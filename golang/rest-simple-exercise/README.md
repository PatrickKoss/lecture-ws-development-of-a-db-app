# Introduction

This is the golang implementation of the simple rest api.

# Getting Started

To instantiate the application, execute the following command line instruction:

```bash
make run
```

For running the test suite associated with this application, utilize the subsequent command:

```bash
make test
```

To auto-generate Swagger API documentation, the following command should be used:

```bash
make swagger
```

Access the generated Swagger documentation via the following URL:

```bash
http://localhost:8081/swagger/
```

# Implementation

1. Define the attributes of the Student data model, alongside its creation and update functionalities.
   Incorporate these structural definitions within the [models](internal/core/students.go) sub-directory.
2. Formulate a database migration script based on the previously defined Student model. Insert this migration script in
   [migration](repository/sqlboiler/migrations/00_create_students.sql)

3. Generate SQLBoiler data models by executing the command:

```bash
make models
```

4. Implement a data repository layer by actualizing the requisite interface within
   the [repository](internal/repository/students.go) sub-directory.

5. Generate mock objects to facilitate unit testing:

```bash
make mocks
```

6. Architect a service layer to encapsulate business logic. This service should validate input data,
   construct Student objects, and interact with the data repository layer. Implement this interface
   within [service](internal/service/students.go).

7. Generate additional mocks analogous to prior steps.
8. Validate the service layer by conducting tests utilizing the repository mocks.
9. Develop an HTTP handler for the REST API. This handler should support CRUD operations including:
    - Create a new student (POST /students)
    - Get all students (GET /students)
    - Update a student (PUT /students/{id})
    - Delete a student (DELETE /students/{id})

The handler is responsible for deserializing the request payload, parsing URL path variables, and interfacing with the
service layer. Additionally, the handler should map service layer errors to appropriate HTTP status codes.
Integrate this handler within internal/adapter/api/student/ and extend the
[error middleware](internal/adapter/api/middleware/error.go) for error mapping.

10. Initialize a Fiber application instance and register the aforementioned HTTP handlers
    in [here](internal/adapter/api/api.go)

11. Validate the HTTP handlers by testing them against service layer mocks.

12. Auto-generate Swagger API documentation, adhering to the given example code snippet for documentation annotations:

```go
// CreateStudent godoc
// @Summary create a new student
// @Description create a new student
// @Accept  json
// @Produce  json
// @Param data body core.UpdateStudent true "student to create"
// @Success 201 {object} core.Student
// @Failure 400 {object} models.ErrorMessage
// @Router /students [post]
// @Tags student
// create route.
func CreateStudent(studentService service.StudentService) fiber.Handler {}
```
