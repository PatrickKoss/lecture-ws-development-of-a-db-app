"""Health views."""

from drf_yasg import openapi
from drf_yasg.utils import swagger_auto_schema
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView


class Health(APIView):
    """Health route."""

    example_cluster_response_post = {
        "200": openapi.Response(
            description="Health route that always return 200 ok.",
        ),
    }

    @swagger_auto_schema(tags=["health"])
    def get(self, request, *args, **kwargs):
        """Get ok."""
        return Response(status=status.HTTP_200_OK)
