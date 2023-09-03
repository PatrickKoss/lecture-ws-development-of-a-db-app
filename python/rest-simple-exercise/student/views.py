from drf_yasg.utils import swagger_auto_schema
from rest_framework import status
from rest_framework.exceptions import NotFound
from rest_framework.response import Response
from rest_framework.views import APIView

from student.models import Student
from student.serializer.serializer import StudentCreateUpdateSerializer, StudentSerializer
from student.documentation import student_swagger


class StudentGetCreateView(APIView):
    def get(self, request, *args, **kwargs):
        """Get all students."""
        return Response(
            data={"message": "Implement me"},
            status=status.HTTP_200_OK,
        )

    def post(self, request):
        return Response(
            data={"message": "implement me"},
            status=status.HTTP_201_CREATED,
        )


class StudentUpdateDeleteView(APIView):
    def put(self, request, id):
        return Response(
            data={"message": "implement me"},
            status=status.HTTP_200_OK,
        )

    def delete(self, request, id):
        return Response(
            data={"message": "implement me"},
            status=status.HTTP_204_NO_CONTENT,
        )
