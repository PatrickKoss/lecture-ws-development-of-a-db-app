from fastapi.testclient import TestClient

from adapters.api.api import app, get_student_service
from adapters.repository.models import Student
from core.student import StudentCreate, StudentUpdate

client = TestClient(app)


# Mock StudentService
class MockStudentService:
    def create_student(self, student: StudentCreate) -> Student:
        return Student(
            id="test",
            mnr=123,
            name="John",
            last_name="Doe",
            email="john.doe@example.com",
        )

    def get_student(self, student_id: str):
        return Student(
            id=student_id,
            mnr=123,
            name="John",
            last_name="Doe",
            email="john.doe@example.com",
        )

    def list_students(self):
        return [
            Student(
                id="test",
                mnr=123,
                name="John",
                last_name="Doe",
                email="john.doe@example.com",
            )
        ]

    def update_student(self, student_id: str, student_update: StudentUpdate) -> Student:
        return Student(
            id=student_id,
            mnr=123,
            name="John",
            last_name="Doe",
            email="john.doe@example.com",
        )

    def delete_student(self, student_id: str):
        pass


# Override the dependency
def override_get_student_service():
    return MockStudentService()


app.dependency_overrides[get_student_service] = override_get_student_service


def test_create_student():
    response = client.post(
        "/students",
        json={
            "mnr": 123,
            "firstName": "John",
            "lastName": "Doe",
            "email": "john.doe@example.com",
        },
    )
    assert response.status_code == 200
    assert response.json() == {
        "id": "test",
        "mnr": 123,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@example.com",
    }


def test_get_student():
    response = client.get("/students/test")
    assert response.status_code == 200
    assert response.json() == {
        "id": "test",
        "mnr": 123,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@example.com",
    }


def test_list_students():
    response = client.get("/students")
    assert response.status_code == 200
    students = response.json()["students"]
    assert len(students) == 1
    assert students[0]["id"] == "test"
    assert students[0]["mnr"] == 123
    assert students[0]["firstName"] == "John"
    assert students[0]["lastName"] == "Doe"
    assert students[0]["email"] == "john.doe@example.com"


def test_update_student():
    response = client.put(
        "/students/test",
        json={
            "mnr": 123,
            "firstName": "John",
            "lastName": "Doe",
            "email": "john.doe@example.com",
        },
    )
    assert response.status_code == 200
    assert response.json() == {
        "id": "test",
        "mnr": 123,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@example.com",
    }


def test_delete_student():
    response = client.delete("/students/1")
    assert response.status_code == 200
    assert response.json() == {"message": "Student deleted"}
