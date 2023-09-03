# Introduction
This is the python implementation of the simple rest api.

# Getting Started
Install dependencies:
```bash
make install
```

Run the application.
```bash
make run
```

Run the test suite:
```bash
make test
```

Create migrations from models:
```bash
make makemigrations
```

Migrate the database:
```bash
make migrate
```

Visit the swagger ui:
```bash
http://localhost:8000/swagger/
```

# Implementation
1. Define the attributes constituting the Student data model and implement methods for creating and updating 
instances of this model. Place these structural definitions within the [models.py](student/models.py) file.
2. Initiate the creation of a database migration script by running the following command:
```bash
make makemigrations
```
Subsequently, execute these migrations on the SQLite database utilizing the command:
```bash
make migrate
```
3. Implement a serializer within the [serializers.py](student/serializer/serializer.py) file to facilitate the 
conversion of the Student data model to a JSON object. Further, implement logic to convert a request payload to a 
corresponding model instance, ensuring persistence in the database. This is achieved by overriding the create and 
update methods in the serializer.
4. Develop an HTTP handler for the REST API. This handler should support CRUD operations including:
    - Create a new student (POST /students)
    - Retrieve all students (GET /students)
    - Update a student by id (PUT /students/{id})
    - Delete a student by id (DELETE /students/{id})   
The handler is tasked with deserializing the incoming request payload, parsing URL path variables, 
and delegating tasks to the serializer and the repository layer. Integrate this HTTP handler within 
the [views.py](student/views.py) file.

5. Execute a comprehensive suite of tests to validate the application's functionality. 
These tests should be located in the [tests.py](student/tests/tests.py) file.
6. Generate Swagger API documentation in an automated fashion. The documentation should be compliant with the sample 
code snippets provided for annotative purposes.

```python
@swagger_auto_schema(
    responses=student_swagger.example_response_get_all,
    tags=["student"],
)
def get(self, request, *args, **kwargs):
    pass
```
