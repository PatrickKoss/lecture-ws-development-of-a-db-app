"""Apps."""

from django.apps import AppConfig


class HealthConfig(AppConfig):
    """Health config."""

    default_auto_field = "django.db.models.BigAutoField"
    name = "health"
