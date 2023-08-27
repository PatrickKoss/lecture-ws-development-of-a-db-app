"""Admin."""

from django.contrib import admin

from accounts.models import Accounts

admin.site.register(Accounts)
