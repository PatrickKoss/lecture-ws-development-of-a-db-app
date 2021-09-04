"""Models."""

from django.db import models


class Help(models.Model):
    """Help model."""

    help_id = models.UUIDField(primary_key=True)
    tip = models.CharField(max_length=300)
