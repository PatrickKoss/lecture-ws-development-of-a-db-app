"""Accounts tips view."""

from datetime import datetime

import pytz
from django.contrib.auth.hashers import make_password
from drf_yasg.utils import swagger_auto_schema
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from utils.helpers import validate_request_body
from .documentation import account_swagger
from .models import Accounts
from .serializer.account_serializer import (
    AccountPostSerializer,
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
        account = AccountPostSerializer(data=json_body)
        if not account.is_valid():
            return Response(
                data={
                    "message": "Sent account data is not valid",
                    "errors": account.errors,
                },
                status=status.HTTP_400_BAD_REQUEST,
            )

        account_data = account.data

        # create account
        try:
            date = datetime.utcnow().replace(tzinfo=pytz.UTC)
            # dont save passwords in clear text
            account_model = Accounts.objects.create(
                username=account_data["username"],
                password=make_password(account_data["password"]),
                # password=account_data["password"],
                email=account_data["email"],
                created_on=date,
            )
        except Exception:
            return Response(
                data={"message": "Account already exists with username or email"},
                status=status.HTTP_400_BAD_REQUEST,
            )

        account = AccountModelListSerializer(account_model)

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

        account_id = kwargs["accountId"]

        # validate account body
        account = AccountPutSerializer(data=json_body)
        if not account.is_valid():
            return Response(
                data={
                    "message": "Account password is not valid",
                    "errors": account.errors,
                },
                status=status.HTTP_400_BAD_REQUEST,
            )

        # check if account exists
        try:
            account_model = Accounts.objects.get(user_id=account_id)
        except Exception:
            return Response(
                data={"message": "Account does not exists"},
                status=status.HTTP_404_NOT_FOUND,
            )

        # save new pw
        account_model.password = make_password(account.data["password"])
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
        account_id = kwargs["accountId"]

        try:
            account = Accounts.objects.get(user_id=account_id)
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
