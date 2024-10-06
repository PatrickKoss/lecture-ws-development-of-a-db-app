import uuid
from copy import deepcopy

from sqlalchemy.orm import Session

from adapters.repository.models import Student
from adapters.repository.student_uow import StudentUnitOfWork
from core.student import StudentCreate, StudentUpdate
from services.exceptions import NotFoundException


class StudentService:
    def __init__(self, session: Session):
        self.uow = StudentUnitOfWork(session)

    def create_student(self, student_data: StudentCreate) -> Student:
        with self.uow:
            student = Student(**student_data.dict())
            student.id = str(uuid.uuid4())
            student = self.uow.students.add(student)
            new_student = deepcopy(student)

        return new_student

    def get_student(self, student_id: str) -> Student:
        with self.uow:
            student = self.uow.students.get(student_id)
            if student is None:
                raise NotFoundException("Student not found")
            student = deepcopy(student)

        return student

    def list_students(self) -> list[Student]:
        with self.uow:
            students = self.uow.students.list()
            students = deepcopy(students)

        return students

    def update_student(self, student_id: str, update_data: StudentUpdate) -> Student:
        with self.uow:
            student = self.uow.students.get(student_id)
            if student is None:
                raise NotFoundException("Student not found")

            if update_data.first_name is not None:
                student.name = update_data.first_name
            if update_data.last_name is not None:
                student.last_name = update_data.last_name
            if update_data.email is not None:
                student.email = update_data.email

            updated_student = deepcopy(student)

            self.uow.students.update(student)

        return updated_student

    def delete_student(self, student_id: str):
        with self.uow:
            student = self.uow.students.get(student_id)
            if student is None:
                raise NotFoundException("Student not found")
            self.uow.students.delete(student)
