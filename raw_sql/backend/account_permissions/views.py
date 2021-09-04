"""Account permission views."""
from typing import List

from django.db import connection
from drf_yasg.utils import swagger_auto_schema
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from account_permissions.documentation import account_permission_swagger
from accounts.models import Accounts
from permissions.models import Permissions
from permissions.serializer.permission_serializer import PermissionListSerializer
from utils.helpers import validate_request_body


def dict_fetch_all(cursor) -> List[dict]:
    """Return all rows from a cursor as a dict."""
    columns = [col[0] for col in cursor.description]
    return [dict(zip(columns, row)) for row in cursor.fetchall()]


class AccountPermission(APIView):
    """Routes for account permissions."""

    @swagger_auto_schema(
        responses=account_permission_swagger.example_response_get_all,
        tags=["account-permissions"],
    )
    def get(self, request, *args, **kwargs):
        """Get all permissions for an account."""
        account_id = kwargs["username"]
        try:
            account = Accounts.objects.only("username", "email").get(
                username=account_id
            )
        except Exception:
            return Response(
                data={
                    "message": "Account does not exists",
                },
                status=status.HTTP_404_NOT_FOUND,
            )
        with connection.cursor() as cursor:
            cursor.execute(
                "SELECT accounts_accounts.username, accounts_accounts.email, accounts_accounts_permissions.permissions_id "
                "FROM accounts_accounts_permissions "
                "INNER JOIN accounts_accounts ON (accounts_accounts.username=accounts_accounts_permissions.accounts_id) "
                "WHERE accounts_id=%s ",
                [account_id],
            )
            account_permissions = dict_fetch_all(cursor)
            permissions = [
                permission["permissions_id"] for permission in account_permissions
            ]
            account = {
                "username": account.username,
                "email": account.email,
                "permissions": permissions,
            }
        return Response(
            data={
                "message": "Successfully got account and its permissions",
                "account": account,
            },
            status=status.HTTP_200_OK,
        )

    @swagger_auto_schema(
        responses=account_permission_swagger.example_response_post,
        request_body=account_permission_swagger.request_body_post,
        tags=["account-permissions"],
    )
    def post(self, request, *args, **kwargs):
        """Create permission for an account."""
        # validate request body
        error, json_body = validate_request_body(request)
        if error is not None:
            return error

        permissions = PermissionListSerializer(data=json_body)
        if not permissions.is_valid():
            return Response(
                data={
                    "message": "Sent permission is not valid",
                    "errors": permissions.errors,
                },
                status=status.HTTP_400_BAD_REQUEST,
            )
        permissions = permissions.data["permissions"]

        # check entry existence
        if not Accounts.objects.filter(username=kwargs["username"]).exists():
            return Response(
                data={
                    "message": "Account does not exist",
                },
                status=status.HTTP_404_NOT_FOUND,
            )
        if Permissions.objects.filter(permission__in=permissions).count() != len(
            permissions
        ):
            return Response(
                data={
                    "message": "One or more of sent permissions does not exists",
                },
                status=status.HTTP_404_NOT_FOUND,
            )

        # create value string to add as entries in db
        values_permission = ""
        for index, perm in enumerate(permissions):
            values_permission += (
                f"('{kwargs['username']}', '{perm}')"
                if index == len(permissions) - 1
                else f"('{kwargs['username']}', '{perm}'),"
            )

        # add new account permissions. If the permission for the account is already in the db then skip insert
        with connection.cursor() as cursor:
            cursor.execute(
                f"INSERT INTO accounts_accounts_permissions (accounts_id, permissions_id)"
                f"SELECT NewEntries.Account, NewEntries.Permission "
                f"FROM (VALUES {values_permission}) AS NewEntries (Account, Permission) WHERE NOT EXISTS "
                f"(SELECT accounts_id, permissions_id FROM accounts_accounts_permissions as aap "
                f"WHERE aap.accounts_id = NewEntries.Account AND aap.permissions_id = NewEntries.Permission)"
            )

        return Response(
            data={
                "message": "Successfully added permissions",
            },
            status=status.HTTP_201_CREATED,
        )


class AccountPermissionSingle(APIView):
    """Routes for account permissions."""

    @swagger_auto_schema(
        responses=account_permission_swagger.example_response_delete,
        tags=["account-permissions"],
    )
    def delete(self, request, *args, **kwargs):
        """Delete permission for an account."""
        permission = kwargs["permission"]
        account_id = kwargs["username"]

        # check for account existence
        try:
            account = Accounts.objects.get(username=account_id)
        except Exception:
            return Response(
                data={
                    "message": "Account does not exist",
                },
                status=status.HTTP_404_NOT_FOUND,
            )

        # check for permission in account existence
        if permission not in [perm.permission for perm in account.permissions.all()]:
            return Response(
                data={
                    "message": "Permission does not exist in account",
                },
                status=status.HTTP_404_NOT_FOUND,
            )

        # delete permission from db
        with connection.cursor() as cursor:
            cursor.execute(
                "DELETE FROM accounts_accounts_permissions WHERE permissions_id=(%s) AND accounts_id=(%s)",
                [permission, account_id],
            )

        return Response(
            data={
                "message": f"Successfully deleted permission {permission} for account {account_id}",
            },
            status=status.HTTP_200_OK,
        )
