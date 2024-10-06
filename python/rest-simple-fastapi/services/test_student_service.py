# test_student_service.py
from unittest.mock import MagicMock, patch

import pytest

from adapters.repository.models import Student
from core.student import StudentCreate, StudentUpdate
from services.exceptions import NotFoundException
from services.student_service import StudentService


@pytest.fixture
def mock_session():
    return MagicMock()


@pytest.fixture
def student_service(mock_session):
    return StudentService(mock_session)


def test_create_student(student_service):
    create_data_dict = {
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@example.com",
        "mnr": 12345,
    }
    student_data = StudentCreate(**create_data_dict)

    with patch.object(student_service.uow.students, "find_by_mnr", return_value=None):
        with patch.object(student_service.uow.students, "add") as mock_add:
            with patch("uuid.uuid4", return_value="1"):
                created_student = student_service.create_student(student_data)
                mock_add.assert_called_once()
                assert created_student.id == "1"
                assert created_student.mnr == 12345


def test_get_student(student_service):
    mock_student = Student(
        id="1", mnr="12345", name="John", last_name="Doe", email="john.doe@example.com"
    )

    with patch.object(student_service.uow.students, "get", return_value=mock_student):
        student = student_service.get_student("1")
        assert student.id == "1"
        assert student.mnr == "12345"


def test_get_student_not_found(student_service):
    with patch.object(student_service.uow.students, "get", return_value=None):
        with pytest.raises(NotFoundException):
            student_service.get_student("1")


def test_update_student(student_service):
    mock_student = Student(
        id="1", mnr=123, name="John", last_name="Doe", email="john.doe@example.com"
    )
    update_data_dict = {
        "firstName": "Jane",
        "lastName": "Smith",
        "email": "jane.smith@example.com",
    }
    update_data = StudentUpdate(**update_data_dict)

    with patch.object(student_service.uow.students, "get", return_value=mock_student):
        with patch.object(student_service.uow.students, "update") as mock_update:
            updated_student = student_service.update_student("1", update_data)
            mock_update.assert_called_once()
            assert updated_student.name == "Jane"
            assert updated_student.last_name == "Smith"
            assert updated_student.email == "jane.smith@example.com"


def test_delete_student(student_service):
    mock_student = Student(
        id="1", mnr="12345", name="John", last_name="Doe", email="john.doe@example.com"
    )

    with patch.object(student_service.uow.students, "get", return_value=mock_student):
        with patch.object(student_service.uow.students, "delete") as mock_delete:
            student_service.delete_student("1")
            mock_delete.assert_called_once()


def test_delete_student_not_found(student_service):
    with patch.object(student_service.uow.students, "get", return_value=None):
        with pytest.raises(NotFoundException):
            student_service.delete_student("1")
