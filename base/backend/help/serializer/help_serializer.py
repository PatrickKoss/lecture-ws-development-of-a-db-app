from rest_framework import serializers

from utils.basic_serializer import char_regex_validator_string_space
from ..models import Help


class HelpModelSerializer(serializers.HyperlinkedModelSerializer):
    help_id = serializers.UUIDField(required=True)
    tip = serializers.CharField(
        default="",
        max_length=300,
        validators=[char_regex_validator_string_space],
        min_length=1,
    )

    def to_representation(self, instance):
        data = super(HelpModelSerializer, self).to_representation(instance)
        if "help_id" in data:
            data.update({"id": data["help_id"]})
            del data["help_id"]
        return data

    class Meta:
        model = Help
        fields = [
            "help_id",
            "tip",
        ]


class HelpResponseSerializer(serializers.Serializer):
    help_id = serializers.UUIDField(required=True)
    tip = serializers.CharField(required=True, max_length=300, min_length=1)

    def get_fields(self):
        result = super(HelpResponseSerializer, self).get_fields()
        id_ = result.pop("help_id")
        result["id"] = id_
        return result


class HelpSerializer(serializers.Serializer):
    message = serializers.CharField()
    tips = HelpResponseSerializer(many=True)


class HelpSingleSerializer(serializers.Serializer):
    message = serializers.CharField()
    tip = HelpResponseSerializer()
