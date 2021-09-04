from django.urls import reverse
from model_bakery import baker
from rest_framework import status
from rest_framework.response import Response
from rest_framework.test import APIClient, APITestCase

from permissions.models import Permissions


class CheckPermissions(APITestCase):
    def setUp(self):
        self.client: APIClient = APIClient()
        self.maxDiff = None
        self.url: str = reverse(
            "permission",
        )
        self.url_single: str = reverse(
            "permission_single",
            kwargs={
                "permission": "test",
            },
        )
        self.permission_post = {
            "permission": "test",
        }

    def test_get_all_accounts(self):
        test_instance = baker.make(
            "Permissions",
        )
        test_instance.save()
        response: Response = self.client.get(path=self.url, format="json")
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(
            len(response.json()["permissions"]), len(Permissions.objects.all())
        )

    def test_post(self):
        response: Response = self.client.post(
            path=self.url, data=self.permission_post, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(Permissions.objects.get(permission="test").permission, "test")
        response: Response = self.client.post(
            path=self.url, data=self.permission_post, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_post_invalid(self):
        self.permission_post.update({"permission": True})
        response: Response = self.client.post(
            path=self.url, data=self.permission_post, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_delete_not_found(self):
        self.url_single: str = reverse(
            "permission_single",
            kwargs={
                "permission": "not_found",
            },
        )
        response: Response = self.client.delete(path=self.url_single, format="json")
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_delete(self):
        self.url_single: str = reverse(
            "permission_single",
            kwargs={
                "permission": "test2",
            },
        )
        test_instance = baker.make("Permissions", permission="test2")
        test_instance.save()
        response: Response = self.client.delete(path=self.url_single, format="json")
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        permissions = [perm["permission"] for perm in Permissions.objects.all()]
        self.assertEqual("test2" not in permissions, True)
