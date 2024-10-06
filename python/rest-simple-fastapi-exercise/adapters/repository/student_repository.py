from typing import Type

from sqlalchemy.orm import Session

from adapters.repository.models import Student


class StudentRepository:
    def __init__(self, session: Session):
        self.session = session

    # TODO implement the methods
