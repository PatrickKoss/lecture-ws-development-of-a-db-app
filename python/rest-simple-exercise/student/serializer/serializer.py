from rest_framework import serializers

from student.models import Student


class StudentSerializer(serializers.ModelSerializer):
    # TODO implement
    pass


class StudentCreateUpdateSerializer(serializers.ModelSerializer):
    # TODO implement
    pass


class StudentListSerializer(serializers.Serializer):
    message = serializers.CharField()
    students = StudentSerializer(many=True)
