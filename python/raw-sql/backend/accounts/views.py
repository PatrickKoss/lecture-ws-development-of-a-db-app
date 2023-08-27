"""Accounts tips view."""

from django.contrib.auth.hashers import make_password
from drf_yasg.utils import swagger_auto_schema
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from utils.helpers import validate_request_body
from .documentation import account_swagger
from .models import Accounts
from .serializer.account_serializer import (
    AccountModelSerializer,
    AccountPutSerializer,
    AccountModelListSerializer,
)


class Account(APIView):
    """Routes for accounts."""

    @swagger_auto_schema(
        responses=account_swagger.example_response_get_all,
        tags=["accounts"],
    )
    def get(self, request, *args, **kwargs):
        """Get all accounts."""
        accounts = Accounts.objects.all()
        print(accounts.query)
        accounts = AccountModelListSerializer(accounts, many=True).data

        return Response(
            data={"message": "List of accounts", "accounts": accounts},
            status=status.HTTP_200_OK,
        )

    @swagger_auto_schema(
        responses=account_swagger.example_response_post,
        request_body=account_swagger.request_body_post,
        tags=["accounts"],
    )
    def post(self, request, *args, **kwargs):
        """Create account."""
        # validate request body
        error, json_body = validate_request_body(request)
        if error is not None:
            return error

        # validate account body
        account = AccountModelSerializer(data=json_body)
        if not account.is_valid():
            return Response(
                data={
                    "message": "Sent account data is not valid",
                    "errors": account.errors,
                },
                status=status.HTTP_400_BAD_REQUEST,
            )

        # create account
        try:
            account.save()
        except Exception:
            return Response(
                data={"message": "Account already exists"},
                status=status.HTTP_400_BAD_REQUEST,
            )

        # serialize response
        account = AccountModelListSerializer(data=json_body)
        account.is_valid()

        return Response(
            data={
                "message": "Created account",
                "account": account.data,
            },
            status=status.HTTP_201_CREATED,
        )


class AccountSingle(APIView):
    """Routes for accounts."""

    @swagger_auto_schema(
        responses=account_swagger.example_response_put,
        request_body=account_swagger.request_body_put,
        tags=["accounts"],
    )
    def put(self, request, *args, **kwargs):
        """Update account."""
        error, json_body = validate_request_body(request)
        if error is not None:
            return error

        account_username = kwargs["username"]

        # validate account body
        account = AccountPutSerializer(data=json_body)
        if not account.is_valid():
            return Response(
                data={
                    "message": "Account password or email is not valid",
                    "errors": account.errors,
                },
                status=status.HTTP_400_BAD_REQUEST,
            )
        account = account.data

        # check if account exists
        try:
            account_model = Accounts.objects.get(username=account_username)
        except Exception:
            return Response(
                data={"message": "Account does not exists"},
                status=status.HTTP_404_NOT_FOUND,
            )

        if "password" in account:
            account_model.password = make_password(account["password"])
        if "email" in account:
            account_model.email = account["email"]
        if "email" in account or "password" in account:
            account_model.save()

        account = AccountModelListSerializer(account_model)

        return Response(
            data={"message": "Successfully updated account", "account": account.data},
            status=status.HTTP_200_OK,
        )

    @swagger_auto_schema(
        responses=account_swagger.example_response_delete,
        tags=["accounts"],
    )
    def delete(self, request, *args, **kwargs):
        """Delete account."""
        try:
            account = Accounts.objects.get(username=kwargs["username"])
        except Exception:
            return Response(
                data={"message": "Account does not exists"},
                status=status.HTTP_404_NOT_FOUND,
            )
        account.delete()
        return Response(
            data={
                "message": "Successfully deleted account",
            },
            status=status.HTTP_200_OK,
        )
