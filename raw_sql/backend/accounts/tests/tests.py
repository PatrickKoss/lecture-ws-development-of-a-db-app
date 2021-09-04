from django.urls import reverse
from model_bakery import baker
from rest_framework import status
from rest_framework.response import Response
from rest_framework.test import APIClient, APITestCase

from accounts.models import Accounts


class CheckAccount(APITestCase):
    def setUp(self):
        self.client: APIClient = APIClient()
        self.maxDiff = None
        self.url: str = reverse(
            "account",
        )
        self.url_single: str = reverse(
            "account_single",
            kwargs={
                "username": "test test",
            },
        )
        self.account_post = {
            "username": "test test",
            "password": "test",
            "email": "test@gmail.com",
        }
        self.account_put = {"password": "test test", "email": "test2@gmail.com"}

    def test_get_all_accounts(self):
        test_instance = baker.make(
            "Accounts",
        )
        test_instance.save()
        response: Response = self.client.get(path=self.url, format="json")
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(len(response.json()["accounts"]), len(Accounts.objects.all()))

    def test_post(self):
        response: Response = self.client.post(
            path=self.url, data=self.account_post, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(response.json()["account"]["username"], "test test")

    def test_put_invalid_body(self):
        self.account_put.update({"password": "@@@" * 100})
        response: Response = self.client.put(
            path=self.url_single, data=self.account_put, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_put(self):
        test_instance = baker.make("Accounts", username="test test", password="123")
        test_instance.save()
        response: Response = self.client.put(
            path=self.url_single, data=self.account_put, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.json()["account"]["username"], "test test")

    def test_put_not_found(self):
        self.url_single: str = reverse(
            "account_single",
            kwargs={
                "username": "test3",
            },
        )
        response: Response = self.client.put(
            path=self.url_single, data=self.account_put, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_delete_not_found(self):
        self.url_single: str = reverse(
            "account_single",
            kwargs={
                "username": "test3",
            },
        )
        response: Response = self.client.delete(path=self.url_single, format="json")
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_delete(self):
        self.url_single: str = reverse(
            "account_single",
            kwargs={
                "username": "test2",
            },
        )
        test_instance = baker.make("Accounts", username="test2", password="123")
        test_instance.save()
        response: Response = self.client.delete(path=self.url_single, format="json")
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        accounts = [account["username"] for account in Accounts.objects.all()]
        self.assertEqual("test2" not in accounts, True)
