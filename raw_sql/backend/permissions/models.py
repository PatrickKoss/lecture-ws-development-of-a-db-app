"""Models."""

from django.db import models


class Permissions(models.Model):
    """Permission model."""

    permission = models.CharField(primary_key=True, max_length=200)
