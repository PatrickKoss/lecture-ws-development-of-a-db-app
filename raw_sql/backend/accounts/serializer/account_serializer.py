from accounts.models import Accounts
from django.contrib.auth.hashers import make_password
from permissions.serializer.permission_serializer import PermissionModelSerializer
from rest_framework import serializers


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

    def create(self, validated_data):
        validated_data.update({"password": make_password(validated_data.pop('password'))})
        account = super().create(validated_data)
        return account

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
