from django.urls import reverse
from model_bakery import baker
from rest_framework import status
from rest_framework.response import Response
from rest_framework.test import APIClient, APITestCase

from help.models import Help


class CheckHelp(APITestCase):
    def setUp(self):
        self.client: APIClient = APIClient()
        self.maxDiff = None
        self.url: str = reverse(
            "help",
        )
        self.url_single: str = reverse(
            "help_single",
            kwargs={
                "tipId": "142072c9-fb71-40f4-bbf1-670fbc5fab4f",
            },
        )
        self.tip = {
            "tip": "test test",
        }

    def test_get_all_tips(self):
        test_instance = baker.make(
            "Help",
        )
        test_instance.save()
        response: Response = self.client.get(path=self.url, format="json")
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(len(response.json()["tips"]), len(Help.objects.all()))

    def test_post(self):
        response: Response = self.client.post(
            path=self.url, data=self.tip, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(response.json()["tip"]["tip"], "test test")

    def test_put_invalid_body(self):
        self.tip.update({"tip": "@@@"})
        response: Response = self.client.put(
            path=self.url_single, data=self.tip, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_put(self):
        test_instance = baker.make(
            "Help", help_id="142072c9-fb71-40f4-bbf1-670fbc5fab4f"
        )
        test_instance.save()
        response: Response = self.client.put(
            path=self.url_single, data=self.tip, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.json()["tip"]["tip"], "test test")
        self.assertEqual(
            Help.objects.get(help_id="142072c9-fb71-40f4-bbf1-670fbc5fab4f").tip,
            "test test",
        )

    def test_put_not_found(self):
        self.url_single: str = reverse(
            "help_single",
            kwargs={
                "tipId": "d05a85d2-2d4c-447d-b043-705b15eaa7c5",
            },
        )
        response: Response = self.client.put(
            path=self.url_single, data=self.tip, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_delete_not_found(self):
        self.url_single: str = reverse(
            "help_single",
            kwargs={
                "tipId": "d05a85d2-2d4c-447d-b043-705b15eaa7c5",
            },
        )
        response: Response = self.client.delete(
            path=self.url_single, data=self.tip, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_delete(self):
        self.url_single: str = reverse(
            "help_single",
            kwargs={
                "tipId": "c9b6e864-8d74-4af6-a9ab-2a6fef63718f",
            },
        )
        test_instance = baker.make(
            "Help", help_id="c9b6e864-8d74-4af6-a9ab-2a6fef63718f"
        )
        test_instance.save()
        response: Response = self.client.delete(
            path=self.url_single, data=self.tip, format="json"
        )
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        tips = [tip["help_id"] for tip in Help.objects.all()]
        self.assertEqual("c9b6e864-8d74-4af6-a9ab-2a6fef63718f" not in tips, True)
