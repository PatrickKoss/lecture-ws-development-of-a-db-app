from drf_yasg.utils import swagger_auto_schema
from rest_framework import status
from rest_framework.exceptions import NotFound
from rest_framework.response import Response
from rest_framework.views import APIView

from student.models import Student
from student.serializer.serializer import StudentCreateUpdateSerializer, StudentSerializer
from student.documentation import student_swagger


class StudentGetCreateView(APIView):
    @swagger_auto_schema(
        responses=student_swagger.example_response_get_all,
        tags=["student"],
    )
    def get(self, request, *args, **kwargs):
        """Get all students."""
        students = Student.objects.all()
        students = StudentSerializer(students, many=True).data

        return Response(
            data={"message": "Successfully got all students", "students": students},
            status=status.HTTP_200_OK,
        )

    @swagger_auto_schema(
        responses=student_swagger.example_response_post,
        request_body=student_swagger.request_body,
        tags=["student"],
    )
    def post(self, request):
        serializer = StudentCreateUpdateSerializer(data=request.data)
        if serializer.is_valid():
            student = serializer.save()
            response_serializer = StudentSerializer(student)
            return Response(response_serializer.data, status=status.HTTP_201_CREATED)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class StudentUpdateDeleteView(APIView):
    @swagger_auto_schema(
        responses=student_swagger.example_response_put,
        request_body=student_swagger.request_body,
        tags=["student"],
    )
    def put(self, request, id):
        try:
            student = Student.objects.get(id=id)
        except Student.DoesNotExist:
            raise NotFound("Student with the given ID does not exist")

        serializer = StudentCreateUpdateSerializer(student, data=request.data)
        if serializer.is_valid():
            updated_student = serializer.save()
            response_serializer = StudentSerializer(updated_student)
            return Response(response_serializer.data)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    @swagger_auto_schema(
        responses=student_swagger.example_response_delete,
        tags=["student"],
    )
    def delete(self, request, id):
        try:
            student = Student.objects.get(id=id)
        except Student.DoesNotExist:
            raise NotFound("Student with the given ID does not exist")

        student.delete()

        return Response(status=status.HTTP_204_NO_CONTENT)
