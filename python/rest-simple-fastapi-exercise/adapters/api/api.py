import uuid

from fastapi import FastAPI, Depends, HTTPException

from adapters.api.error_middleware import ExceptionHandlingMiddleware
from adapters.repository.database import SessionLocal, engine
from adapters.repository.models import Base, Student
from adapters.repository.student_repository import StudentRepository

# Create the database tables
Base.metadata.create_all(bind=engine)

app = FastAPI()

# app.add_middleware(ExceptionHandlingMiddleware)


def get_student_repository():
    db = SessionLocal()
    try:
        yield StudentRepository(db)
    finally:
        db.close()


# TODO implement the crud methods
