"""Permission views."""
from django.db import connection
from drf_yasg.utils import swagger_auto_schema
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from permissions.models import Permissions
from utils.helpers import validate_request_body
from .documentation import permission_swagger
from .serializer.permission_serializer import PermissionModelSerializer


class Permission(APIView):
    """Routes for permissions."""

    @swagger_auto_schema(
        responses=permission_swagger.example_response_get_all,
        tags=["permissions"],
    )
    def get(self, request, *args, **kwargs):
        """Get all permissions."""
        permissions = [
            p.permission
            for p in Permissions.objects.raw("SELECT * FROM permissions_permissions")
        ]
        return Response(
            data={"message": "List of permissions", "permissions": permissions},
            status=status.HTTP_200_OK,
        )

    @swagger_auto_schema(
        responses=permission_swagger.example_response_post,
        request_body=permission_swagger.request_body_post,
        tags=["permissions"],
    )
    def post(self, request, *args, **kwargs):
        """Create permission."""
        # validate request body
        error, json_body = validate_request_body(request)
        if error is not None:
            return error

        permission = PermissionModelSerializer(data=json_body)
        if not permission.is_valid():
            return Response(
                data={
                    "message": "Sent permission is not valid",
                    "errors": permission.errors,
                },
                status=status.HTTP_400_BAD_REQUEST,
            )

        try:
            with connection.cursor() as cursor:
                cursor.execute(
                    "INSERT INTO permissions_permissions (permission) VALUES (%s)",
                    [permission.data["permission"]],
                )
        except Exception:
            return Response(
                data={
                    "message": "Permission already exists",
                },
                status=status.HTTP_400_BAD_REQUEST,
            )

        return Response(
            data={
                "message": "Created permission",
                "permission": permission.data["permission"],
            },
            status=status.HTTP_201_CREATED,
        )


class PermissionSingle(APIView):
    """Routes for permissions."""

    @swagger_auto_schema(
        responses=permission_swagger.example_response_delete,
        tags=["permissions"],
    )
    def delete(self, request, *args, **kwargs):
        """Delete permission."""
        permission = kwargs["permission"]

        if not Permissions.objects.filter(permission=permission).exists():
            return Response(
                data={
                    "message": "Permission does not exists",
                },
                status=status.HTTP_404_NOT_FOUND,
            )

        with connection.cursor() as cursor:
            cursor.execute(
                "DELETE FROM permissions_permissions WHERE permission=(%s)",
                [permission],
            )

        return Response(
            data={
                "message": "Successfully deleted permission",
            },
            status=status.HTTP_200_OK,
        )
