from drf_yasg import openapi

from utils.basic_serializer import (
    PermissionDeniedSerializer,
    MessageSerializer,
)
from ..serializer.help_serializer import HelpSerializer, HelpSingleSerializer

application_json = "application/json"
permission_denied_response = "You do not have permission to perform this action."
permission_denied_description = "You are not authorized"

example_tip = {
    "id": "2f209d6c-cebd-4ebc-ba15-77555a45afa2",
    "tip": "This is a tip",
}

example_response_get_all = {
    "200": openapi.Response(
        description="Get all helping tips",
        schema=HelpSerializer(),
        examples={
            application_json: {
                "message": "Successfully got all helping tips",
                "help": [example_tip],
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
        description="Created helping tip",
        schema=HelpSingleSerializer(),
        examples={
            application_json: {
                "message": "Tip successfully created",
                "tip": example_tip,
            }
        },
    ),
    "400": openapi.Response(
        description="Helping tip is not valid",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Helping tip is not valid",
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
        schema=HelpSingleSerializer(),
        examples={
            application_json: {
                "message": "Successfully updated helping tip",
                "tip": example_tip,
            }
        },
    ),
    "400": openapi.Response(
        description="Helping tip is not valid",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Helping tip is not valid",
            }
        },
    ),
    "404": openapi.Response(
        description="Helping tip does not exists",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Helping tip does not exists",
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
        description="Deleted helping tip",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Successfully deleted helping tip",
            }
        },
    ),
    "404": openapi.Response(
        description="Helping tip does not exists",
        schema=MessageSerializer(),
        examples={
            application_json: {
                "message": "Helping tip does not exists",
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

request_body = openapi.Schema(
    type=openapi.TYPE_OBJECT,
    required=[
        "tip",
    ],
    properties={
        "tip": openapi.Schema(
            type=openapi.TYPE_STRING, description="Tip", max_length=300, min_length=1
        ),
    },
)
