from django.db import connection
from django.urls import reverse
from model_bakery import baker
from rest_framework import status
from rest_framework.response import Response
from rest_framework.test import APIClient, APITestCase

from db_first.models import Accounts


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
                "accountId": 1,
            },
        )
        self.account_post = {
            "username": "test test",
            "password": "test",
            "email": "test@gmail.com",
        }
        self.account_put = {
            "password": "test test",
        }
        with connection.schema_editor() as schema_editor:
            schema_editor.create_model(Accounts)

            if Accounts._meta.db_table not in connection.introspection.table_names():
                raise ValueError(
                    "Table `{table_name}` is missing in test database.".format(
                        table_name=Accounts._meta.db_table
                    )
                )

    def tearDown(self):
        super().tearDown()

        with connection.schema_editor() as schema_editor:
            schema_editor.delete_model(Accounts)

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
        self.account_put.update({"password": "@@@" * 30})
        response: Response = self.client.put(
            path=self.url_single, data=self.account_put, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_put(self):
        test_instance = baker.make("Accounts", user_id=1, username="test test")
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
                "accountId": 2,
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
                "accountId": 2,
            },
        )
        response: Response = self.client.delete(path=self.url_single, format="json")
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_delete(self):
        self.url_single: str = reverse(
            "account_single",
            kwargs={
                "accountId": 3,
            },
        )
        test_instance = baker.make("Accounts", user_id=3)
        test_instance.save()
        response: Response = self.client.delete(path=self.url_single, format="json")
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        accounts = [account["user_id"] for account in Accounts.objects.all()]
        self.assertEqual(3 not in accounts, True)
