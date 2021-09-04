"""Helping tips view."""

import uuid

from drf_yasg.utils import swagger_auto_schema
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from utils.helpers import validate_request_body
from .documentation import help_swagger
from .models import Help as HelpModel
from .serializer.help_serializer import HelpModelSerializer


class Help(APIView):
    """Routes for helping tips."""

    @swagger_auto_schema(
        responses=help_swagger.example_response_get_all,
        tags=["help"],
    )
    def get(self, request, *args, **kwargs):
        """Get all helping tips."""
        help_tips = HelpModel.objects.all()
        help_tips = HelpModelSerializer(help_tips, many=True).data

        return Response(
            data={"message": "List of tips", "tips": help_tips},
            status=status.HTTP_200_OK,
        )

    @swagger_auto_schema(
        responses=help_swagger.example_response_post,
        request_body=help_swagger.request_body,
        tags=["help"],
    )
    def post(self, request, *args, **kwargs):
        """Create helping tip."""
        # validate request body
        error, json_body = validate_request_body(request)
        if error is not None:
            return error

        # update id
        json_body.update({"help_id": uuid.uuid4()})

        # create helping tip
        tip = HelpModelSerializer(data=json_body)
        if not tip.is_valid():
            return Response(
                data={"message": "Helping tip is not valid", "errors": tip.errors},
                status=status.HTTP_400_BAD_REQUEST,
            )
        tip.save()

        return Response(
            data={
                "message": "Created helping tip",
                "tip": tip.data,
            },
            status=status.HTTP_201_CREATED,
        )


class HelpSingle(APIView):
    """Routes for helping tips."""

    @swagger_auto_schema(
        responses=help_swagger.example_response_put,
        request_body=help_swagger.request_body,
        tags=["help"],
    )
    def put(self, request, *args, **kwargs):
        """Update helping tip."""
        error, json_body = validate_request_body(request)
        if error is not None:
            return error

        tip_id = kwargs["tipId"]

        # update id
        json_body.update({"help_id": tip_id})

        # validate
        tip = HelpModelSerializer(data=json_body)
        if not tip.is_valid():
            return Response(
                data={"message": "Helping tip is not valid", "errors": tip.errors},
                status=status.HTTP_400_BAD_REQUEST,
            )

        # check if tip exists
        try:
            tip_model = HelpModel.objects.get(help_id=tip_id)
        except Exception:
            return Response(
                data={"message": "Helping tip does not exists"},
                status=status.HTTP_404_NOT_FOUND,
            )
        tip_model.tip = json_body["tip"]
        tip_model.save()

        return Response(
            data={"message": "Successfully updated helping tip", "tip": tip.data},
            status=status.HTTP_200_OK,
        )

    @swagger_auto_schema(
        responses=help_swagger.example_response_delete,
        tags=["help"],
    )
    def delete(self, request, *args, **kwargs):
        """Delete helping tip."""
        tip_id = kwargs["tipId"]

        try:
            tip = HelpModel.objects.get(help_id=tip_id)
        except Exception:
            return Response(
                data={"message": "Helping tip does not exists"},
                status=status.HTTP_404_NOT_FOUND,
            )
        tip.delete()
        return Response(
            data={
                "message": "Successfully deleted helping tip",
            },
            status=status.HTTP_200_OK,
        )
