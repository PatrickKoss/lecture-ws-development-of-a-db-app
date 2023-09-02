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
        response = self.client.get(self.students_list_url)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(len(response.data['students']), 5)

    def test_create_student(self):
        student_data = {"name": "John", "lastName": "Doe"}
        response = self.client.post(self.students_list_url, student_data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(Student.objects.count(), 6)
        self.assertEqual(response.data['name'], student_data['name'])
        self.assertEqual(response.data['lastName'], student_data['lastName'])

    def test_update_student(self):
        student_data = {"name": "Updated", "lastName": "Name"}
        response = self.client.put(self.student_detail_url, student_data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        updated_student = Student.objects.get(id=self.students[0].id)
        self.assertEqual(updated_student.name, student_data['name'])
        self.assertEqual(updated_student.last_name, student_data['lastName'])

    def test_delete_student(self):
        response = self.client.delete(self.student_detail_url)
        self.assertEqual(response.status_code, status.HTTP_204_NO_CONTENT)
        self.assertRaises(Student.DoesNotExist, Student.objects.get, id=self.students[0].id)
