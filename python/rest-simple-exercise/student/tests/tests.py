from django.urls import reverse
from rest_framework.test import APITestCase
from rest_framework import status
from model_bakery import baker

from student.models import Student

class StudentAPITests(APITestCase):
    def setUp(self):
        # Create some initial students using model_bakery for GET tests
        self.students = baker.make('student.Student', _quantity=5)
        self.student_detail_url = reverse('students_single', kwargs={"id": self.students[0].id})
        self.students_list_url = reverse('students')

    def test_get_all_students(self):
        # TODO implement
        pass

    def test_create_student(self):
        # TODO implement
        pass

    def test_update_student(self):
        # TODO implement
        pass

    def test_delete_student(self):
        # TODO implement
        pass
