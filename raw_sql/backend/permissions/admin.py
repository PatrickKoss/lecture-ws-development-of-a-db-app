"""Admin."""

from django.contrib import admin

from permissions.models import Permissions

admin.site.register(Permissions)
