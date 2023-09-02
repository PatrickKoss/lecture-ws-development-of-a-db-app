from drf_yasg import openapi

from utils.basic_serializer import (
    PermissionDeniedSerializer,
    MessageSerializer,
)
from ..serializer.account_serializer import (
    AccountResponseSerializer,
    AccountSingleResponseSerializer,
)

application_json = "application/json"
permission_denied_response = "You do not have permission to perform this action."
permission_denied_description = "You are not authorized"

example_account = {"username": "test", "email": "test@gmail.com", "userId": 1}

example_response_get_all = {
    "200": openapi.Response(
        description="Get all accounts",
        schema=AccountResponseSerializer(),
        examples={
            application_json: {
                "message": "Successfully got all accounts",
                "accounts": [example_account],
            }
        },
    ),
    "403": openapi.Response(
        description=permission_denied_response,
        schema=PermissionDeniedSerializer,
        examples={
            application_json: {
                "detail": permission_denied_response,
            }
        },
    ),
}

example_response_post = {
    "201": openapi.Response(
        description="Create account",
        schema=AccountSingleResponseSerializer(),
        examples={
            application_json: {
                "message": "Account successfully created",
                "account": example_account,
            }
        },
    ),
    "400": openapi.Response(
        description="Account is not valid",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Account is not valid",
            }
        },
    ),
    "403": openapi.Response(
        description=permission_denied_response,
        schema=PermissionDeniedSerializer,
        examples={
            application_json: {
                "detail": permission_denied_response,
            }
        },
    ),
}

example_response_put = {
    "200": openapi.Response(
        description="Updated helping tip",
        schema=AccountSingleResponseSerializer(),
        examples={
            application_json: {
                "message": "Successfully updated account",
                "account": example_account,
            }
        },
    ),
    "400": openapi.Response(
        description="Account is not valid",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Account is not valid",
            }
        },
    ),
    "404": openapi.Response(
        description="Account does not exists",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Account does not exists",
            }
        },
    ),
    "403": openapi.Response(
        description=permission_denied_response,
        schema=PermissionDeniedSerializer,
        examples={
            application_json: {
                "detail": permission_denied_response,
            }
        },
    ),
}

example_response_delete = {
    "200": openapi.Response(
        description="Delete account",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Successfully deleted account",
            }
        },
    ),
    "404": openapi.Response(
        description="Account does not exists",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Account does not exists",
            }
        },
    ),
    "403": openapi.Response(
        description=permission_denied_response,
        schema=PermissionDeniedSerializer,
        examples={
            application_json: {
                "detail": permission_denied_response,
            }
        },
    ),
}

request_body_post = openapi.Schema(
    type=openapi.TYPE_OBJECT,
    required=[
        "username",
        "password",
        "email",
    ],
    properties={
        "username": openapi.Schema(
            type=openapi.TYPE_STRING,
            description="Username",
            max_length=50,
            min_length=1,
        ),
        "password": openapi.Schema(
            type=openapi.TYPE_STRING,
            description="Password",
            max_length=50,
            min_length=1,
        ),
        "email": openapi.Schema(
            type=openapi.TYPE_STRING, description="Email", max_length=255, min_length=1
        ),
    },
)

request_body_put = openapi.Schema(
    type=openapi.TYPE_OBJECT,
    required=[
        "password",
    ],
    properties={
        "password": openapi.Schema(
            type=openapi.TYPE_STRING,
            description="Password",
            max_length=50,
            min_length=1,
        ),
    },
)
