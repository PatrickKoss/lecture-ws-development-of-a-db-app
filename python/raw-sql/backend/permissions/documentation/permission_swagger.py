from drf_yasg import openapi

from permissions.serializer.permission_serializer import (
    PermissionRespondSerializer,
    PermissionSingleRespondSerializer,
)
from utils.basic_serializer import (
    PermissionDeniedSerializer,
    MessageSerializer,
)

application_json = "application/json"
permission_denied_response = "You do not have permission to perform this action."
permission_denied_description = "You are not authorized"

example_response_get_all = {
    "200": openapi.Response(
        description="Get all permissions",
        schema=PermissionRespondSerializer(),
        examples={
            application_json: {
                "message": "Successfully got all permissions",
                "permissions": ["admin"],
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
        description="Create permission",
        schema=PermissionSingleRespondSerializer(),
        examples={
            application_json: {
                "message": "Permission successfully created",
                "permission": "admins",
            }
        },
    ),
    "400": openapi.Response(
        description="Permission is not valid",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Permission is not valid",
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
        description="Delete permission",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Successfully deleted permission",
            }
        },
    ),
    "404": openapi.Response(
        description="Permission does not exists",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Permission does not exists",
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
        "permission",
    ],
    properties={
        "permission": openapi.Schema(
            type=openapi.TYPE_STRING,
            description="Permission",
            max_length=200,
            min_length=1,
        ),
    },
)
