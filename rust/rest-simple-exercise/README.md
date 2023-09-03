# Introduction
This is the rust implementation of the simple rest api. 

# Getting Started
Before running any command remember to export the DATABASE_URL environment variable. 
```bash
export DATABASE_URL=sqlite://$PWD/students.db
```

Run the application.
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
   [model](db/src/lib.rs) sub-directory.
2. Formulate a database migration script based on the previously defined Student model. Insert this migration script in
   [migration](db/migrations/20230819090247_student.sql)
3. Implement a data repository layer by actualizing the requisite interface within
   the [repository](db/src/lib.rs) sub-directory.
   It should support a deleteById and updateById operation in addition to the default implementations.
4. Architect a service layer to encapsulate business logic. This service should validate input data,
   construct Student objects, and interact with the data repository layer. Implement this interface
   within [service](service/src/lib.rs).
5. Validate the service layer by conducting
   [tests](service/src/lib.rs).
6. Develop an HTTP handler for the REST API. This handler should support CRUD operations including:
    - Create a new student (POST /students)
    - Retrieve all students (GET /students)
    - Update a student by id (PUT /students/{id})
    - Delete a student by id (DELETE /students/{id})
      The handler is responsible for deserializing the request payload, parsing URL path variables, and interfacing with the
      service layer. Additionally, the handler should map service layer errors to appropriate HTTP status codes.
      Integrate this handler within [controller](api/src/student.rs).

7. Validate the controller layer by conducting
   [tests](api/src/student.rs).
8. Auto-generate Swagger API documentation, adhering to the given example code snippet for documentation annotations:

```rust
#[utoipa::path(
responses(
(status = 200, description = "List students", body = ListStudentsResponse, example = json ! (ListStudentsResponse{students: vec ! [Student{id: String::from("14322988-32fe-447c-ac38-06fb6c699b4a"), name: String::from("John"), mnr: 1, created_on: String::from("2021-01-01T00:00:00Z"), last_name: String::from("Doe")}]})),
)
)]
#[get("/students")]
pub(super) async fn list_students(student_service: Data<Box<dyn StudentService>>) -> Result<HttpResponse, ApiError> {}
```
