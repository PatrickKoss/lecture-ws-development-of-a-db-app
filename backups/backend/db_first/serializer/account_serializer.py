from rest_framework import serializers

from db_first.models import Accounts


class AccountModelListSerializer(serializers.HyperlinkedModelSerializer):
    user_id = serializers.UUIDField(read_only=True)
    username = serializers.CharField(read_only=True)
    email = serializers.EmailField(read_only=True)

    def to_representation(self, instance):
        data = super(AccountModelListSerializer, self).to_representation(instance)
        if "user_id" in data:
            data.update({"userId": data["user_id"]})
            del data["user_id"]
        return data

    class Meta:
        model = Accounts
        fields = ["user_id", "username", "email"]


class AccountListSerializer(serializers.Serializer):
    userId = serializers.UUIDField(required=True)
    username = serializers.CharField(required=True, max_length=50)
    email = serializers.EmailField(max_length=200, required=True)


class AccountPutSerializer(serializers.Serializer):
    password = serializers.CharField(
        required=True,
        max_length=50,
    )


class AccountPostSerializer(serializers.Serializer):
    username = serializers.CharField(required=True, max_length=50)
    password = serializers.CharField(
        required=True,
        max_length=50,
    )
    email = serializers.EmailField(max_length=200)


class AccountSingleResponseSerializer(serializers.Serializer):
    message = serializers.CharField()
    account = AccountListSerializer()


class AccountResponseSerializer(serializers.Serializer):
    message = serializers.CharField()
    accounts = AccountListSerializer(many=True)
