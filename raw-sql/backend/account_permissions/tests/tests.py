from django.urls import reverse
from model_bakery import baker
from rest_framework import status
from rest_framework.response import Response
from rest_framework.test import APIClient, APITestCase

from accounts.models import Accounts
from permissions.models import Permissions


class CheckAccountPermissions(APITestCase):
    def setUp(self):
        self.client: APIClient = APIClient()
        self.maxDiff = None
        self.url: str = reverse(
            "account_permission",
            kwargs={"username": "test"},
        )
        self.url_single: str = reverse(
            "account_permission_single",
            kwargs={"permission": "test", "username": "test"},
        )
        self.permission_post = {
            "permissions": ["test"],
        }
        if not Accounts.objects.filter(username="test").exists():
            test_instance = baker.make("Accounts", username="test")
            test_instance.save()
        if not Permissions.objects.filter(permission="test").exists():
            test_instance = baker.make("Permissions", permission="test")
            test_instance.save()

    def test_get_all_account_permissions(self):
        permission = Permissions.objects.get(permission="test")
        account = Accounts.objects.get(username="test")
        account.permissions.add(permission)
        account.save()
        response: Response = self.client.get(path=self.url, format="json")
        account.permissions.remove(permission)
        account.save()
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.json()["account"]["permissions"], ["test"])

    def test_get_all_account_permissions_no_account(self):
        self.url: str = reverse(
            "account_permission",
            kwargs={"username": "test2"},
        )
        response: Response = self.client.get(path=self.url, format="json")
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_post_no_permission(self):
        self.permission_post = {
            "permissions": ["test2"],
        }
        response: Response = self.client.post(
            path=self.url, data=self.permission_post, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_post(self):
        response: Response = self.client.post(
            path=self.url, data=self.permission_post, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        permissions = [
            perm.permission
            for perm in Accounts.objects.get(username="test").permissions.all()
        ]
        self.assertEqual(permissions, ["test"])
        response: Response = self.client.post(
            path=self.url, data=self.permission_post, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

    def test_post_invalid(self):
        response: Response = self.client.post(
            path=self.url, data={"permissions": True}, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_delete_not_found(self):
        self.url_single: str = reverse(
            "account_permission_single",
            kwargs={"permission": "test", "username": "test2"},
        )
        response: Response = self.client.delete(path=self.url_single, format="json")
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_delete_not_found_2(self):
        self.url_single: str = reverse(
            "account_permission_single",
            kwargs={"permission": "test2", "username": "test"},
        )
        response: Response = self.client.delete(path=self.url_single, format="json")
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_delete(self):
        permission = Permissions.objects.get(permission="test")
        account = Accounts.objects.get(username="test")
        account.permissions.add(permission)
        account.save()
        response: Response = self.client.delete(path=self.url_single, format="json")
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        permissions = [
            perm.permission
            for perm in Accounts.objects.get(username="test").permissions.all()
        ]
        self.assertEqual("test" not in permissions, True)
