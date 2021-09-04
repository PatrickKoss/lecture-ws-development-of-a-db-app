"""Apps."""

from django.apps import AppConfig


class DbFirstConfig(AppConfig):
    """Config."""

    default_auto_field = "django.db.models.BigAutoField"
    name = "db_first"
