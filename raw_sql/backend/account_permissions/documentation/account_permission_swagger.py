from drf_yasg import openapi

from accounts.serializer.account_serializer import AccountPermissionResponseSerializer
from utils.basic_serializer import (
    PermissionDeniedSerializer,
    MessageSerializer,
)

application_json = "application/json"
permission_denied_response = "You do not have permission to perform this action."
permission_denied_description = "You are not authorized"

example_account = {
    "username": "test",
    "email": "test@gmail.com",
    "permissions": ["admin", "editor"],
}

example_response_get_all = {
    "200": openapi.Response(
        description="Get all permissions for an account",
        schema=AccountPermissionResponseSerializer(),
        examples={
            application_json: {
                "message": "Successfully got account and its permissions",
                "account": example_account,
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
        description="Add permissions for an account",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Successfully added permissions",
            }
        },
    ),
    "400": openapi.Response(
        description="Validations failed",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Sent permission is not valid",
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
        description="Delete permission for account",
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
        "permissions",
    ],
    properties={
        "permissions": openapi.Schema(
            type=openapi.TYPE_ARRAY,
            description="Permissions to add",
            min_length=1,
            items=openapi.Schema(
                type=openapi.TYPE_STRING, description="Permission", max_length=200
            ),
        ),
    },
)

request_body_put = openapi.Schema(
    type=openapi.TYPE_OBJECT,
    properties={
        "password": openapi.Schema(
            type=openapi.TYPE_STRING,
            description="Password",
            max_length=50,
            min_length=1,
        ),
        "email": openapi.Schema(
            type=openapi.TYPE_STRING, description="Email", max_length=300, min_length=1
        ),
    },
)
