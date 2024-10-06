from typing import Type

from sqlalchemy.orm import Session

from adapters.repository.models import Student


class StudentRepository:
    def __init__(self, session: Session):
        self.session = session

    def add(self, student: Student) -> Student:
        self.session.add(student)
        self.session.flush()
        self.session.refresh(student)
        return student

    def get(self, student_id: str) -> Student:
        return self.session.query(Student).filter(Student.id == student_id).first()

    def list(self) -> list[Type[Student]]:
        return self.session.query(Student).all()

    def update(self, student: Student):
        self.session.merge(student)

    def delete(self, student: Student):
        self.session.delete(student)

    def find_by_mnr(self, mnr: int):
        return self.session.query(Student).filter(Student.mnr == mnr).first()
