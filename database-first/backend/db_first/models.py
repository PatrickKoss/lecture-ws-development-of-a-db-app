# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class AccountRoles(models.Model):
    user = models.OneToOneField("Accounts", models.DO_NOTHING, primary_key=True)
    role = models.ForeignKey("Roles", models.DO_NOTHING)
    grant_date = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = "account_roles"
        unique_together = (("user", "role"),)


class Accounts(models.Model):
    user_id = models.AutoField(primary_key=True)
    username = models.CharField(unique=True, max_length=50)
    password = models.CharField(max_length=1000)
    email = models.CharField(unique=True, max_length=255)
    created_on = models.DateTimeField()
    last_login = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = "accounts"


class Roles(models.Model):
    role_id = models.AutoField(primary_key=True)
    role_name = models.CharField(unique=True, max_length=255)

    class Meta:
        managed = False
        db_table = "roles"
