"""Basic serializer."""

import re

from rest_framework import serializers


class MessageSerializer(serializers.Serializer):
    """Message serializer."""

    message = serializers.CharField(required=True)


class ErrorSerializer(MessageSerializer):
    """Error serializer."""

    errors = serializers.ListField(child=serializers.DictField(), required=False)


class PermissionDeniedSerializer(serializers.Serializer):
    """Permission denied serializer."""

    detail = serializers.CharField(required=True)


def char_regex_validator_string(value: str):
    """Validate regex string without special characters."""
    if not re.match(r"^[a-zA-Z0-9_-]*$", value):
        raise serializers.ValidationError(
            f"{value} is not in the allowed characters: a-zA-Z0-9_-"
        )


def char_regex_validator_string_space(value: str):
    """Validate regex string with space."""
    if not re.match(r"^[ a-zA-Z0-9_-]*$", value):
        raise serializers.ValidationError(
            f"{value} is not in the allowed characters: a-zA-Z0-9_-"
        )
