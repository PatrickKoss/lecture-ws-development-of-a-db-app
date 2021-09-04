"""Models."""

from django.db import models

from permissions.models import Permissions


class Accounts(models.Model):
    """Account model."""

    username = models.CharField(primary_key=True, max_length=100)
    password = models.CharField(max_length=1000)
    email = models.CharField(max_length=300)
    permissions = models.ManyToManyField(Permissions, blank=True)
