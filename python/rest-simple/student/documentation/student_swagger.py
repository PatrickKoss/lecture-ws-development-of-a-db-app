from drf_yasg import openapi

from student.serializer.serializer import StudentCreateUpdateSerializer, StudentListSerializer
from utils.basic_serializer import (
    MessageSerializer,
)

application_json = "application/json"

example_student = {
    "id": "2f209d6c-cebd-4ebc-ba15-77555a45afa2",
    "name": "John",
    "lastName": "Doe",
    "createdAt": "2021-01-01T00:00:00Z",
    "mnr": 123456789,
}

example_response_get_all = {
    "200": openapi.Response(
        description="Get all students",
        schema=StudentListSerializer(),
        examples={
            application_json: {
                "message": "Successfully got all students",
                "students": [example_student],
            }
        },
    ),
}

example_response_post = {
    "201": openapi.Response(
        description="Created student",
        schema=StudentCreateUpdateSerializer(),
        examples={
            application_json: example_student
        },
    ),
    "400": openapi.Response(
        description="Student is not valid",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Student is not valid",
            }
        },
    ),
}

example_response_put = {
    "200": openapi.Response(
        description="Updated student",
        schema=StudentCreateUpdateSerializer(),
        examples={
            application_json: example_student
        },
    ),
    "400": openapi.Response(
        description="Student is not valid",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Student is not valid",
            }
        },
    ),
    "404": openapi.Response(
        description="Student does not exists",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Student does not exists",
            }
        },
    ),
}

example_response_delete = {
    "204": openapi.Response(
        description="Deleted student",
    ),
    "404": openapi.Response(
        description="Student does not exists",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Student does not exists",
            }
        },
    ),
}

request_body = openapi.Schema(
    type=openapi.TYPE_OBJECT,
    required=[
        "name",
        "lastName"
    ],
    properties={
        "name": openapi.Schema(
            type=openapi.TYPE_STRING, description="Name of the student", max_length=200, min_length=1
        ),
        "lastName": openapi.Schema(
            type=openapi.TYPE_STRING, description="Last name of the student", max_length=200, min_length=1
        ),
    },
)
