from rest_framework import serializers

from student.models import Student


class StudentSerializer(serializers.ModelSerializer):
    lastName = serializers.CharField(source='last_name')
    createdAt = serializers.DateTimeField(source='created_at')

    class Meta:
        model = Student
        fields = ('id', 'mnr', 'name', 'lastName', 'createdAt')


class StudentCreateUpdateSerializer(serializers.ModelSerializer):
    name = serializers.CharField(max_length=200, required=True)
    lastName = serializers.CharField(source='last_name', max_length=200, required=True)

    class Meta:
        model = Student
        fields = ('name', 'lastName')

    def create(self, validated_data):
        return Student.objects.create(**validated_data)

    def update(self, instance, validated_data):
        instance.name = validated_data.get('name', instance.name)
        instance.last_name = validated_data.get('last_name', instance.last_name)
        instance.save()
        return instance


class StudentListSerializer(serializers.Serializer):
    message = serializers.CharField()
    students = StudentSerializer(many=True)
