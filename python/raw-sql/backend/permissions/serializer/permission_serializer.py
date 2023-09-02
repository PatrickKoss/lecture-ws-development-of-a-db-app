from rest_framework import serializers

from permissions.models import Permissions


class PermissionModelSerializer(serializers.HyperlinkedModelSerializer):
    permission = serializers.CharField(max_length=200, required=True, min_length=1)

    class Meta:
        model = Permissions
        fields = ["permission"]


class PermissionListSerializer(serializers.Serializer):
    permissions = serializers.ListField(
        child=serializers.CharField(max_length=200, min_length=1), min_length=1
    )


class PermissionRespondSerializer(serializers.Serializer):
    message = serializers.CharField()
    permissions = serializers.ListField(
        child=serializers.CharField(max_length=200, min_length=1)
    )


class PermissionSingleRespondSerializer(serializers.Serializer):
    message = serializers.CharField()
    permission = serializers.CharField(max_length=200, required=True, min_length=1)
