from sqlalchemy.orm import Session

from adapters.repository.student_repository import StudentRepository


class StudentUnitOfWork:
    def __init__(self, session: Session):
        self.session = session
        self.students = StudentRepository(session)

    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        if exc_type is None:
            self.session.commit()
        else:
            self.session.rollback()
        self.session.close()
