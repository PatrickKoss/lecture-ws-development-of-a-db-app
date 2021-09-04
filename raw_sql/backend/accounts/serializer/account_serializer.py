from rest_framework import serializers

from accounts.models import Accounts
from permissions.serializer.permission_serializer import PermissionModelSerializer


class AccountModelListSerializer(serializers.HyperlinkedModelSerializer):
    username = serializers.CharField(max_length=100, required=True, min_length=1)
    email = serializers.EmailField(max_length=300, required=True, min_length=1)
    permissions = PermissionModelSerializer(many=True, read_only=True)

    class Meta:
        model = Accounts
        fields = ["username", "email", "permissions"]


class AccountPermissionSerializer(serializers.Serializer):
    username = serializers.CharField(max_length=100, required=True, min_length=1)
    email = serializers.EmailField(max_length=300, required=True, min_length=1)
    permissions = serializers.ListField(
        child=serializers.CharField(max_length=200, min_length=1), min_length=1
    )


class AccountPermissionResponseSerializer(serializers.Serializer):
    message = serializers.CharField()
    account = AccountPermissionSerializer()


class AccountModelSerializer(AccountModelListSerializer):
    password = serializers.CharField(max_length=50, required=True, min_length=1)

    class Meta:
        model = Accounts
        fields = ["password", "username", "email"]


class AccountPutSerializer(serializers.Serializer):
    password = serializers.CharField(
        max_length=50,
    )
    email = serializers.EmailField(max_length=300)


class AccountSingleResponseSerializer(serializers.Serializer):
    message = serializers.CharField()
    account = AccountModelListSerializer()


class AccountResponseSerializer(serializers.Serializer):
    message = serializers.CharField()
    accounts = AccountModelListSerializer(many=True)
