import uuid

from fastapi import FastAPI, Depends, HTTPException

from adapters.api.error_middleware import ExceptionHandlingMiddleware
from adapters.repository.database import SessionLocal, engine
from adapters.repository.models import Base, Student
from adapters.repository.student_repository import StudentRepository
from core.message import Message
from core.student import StudentCreate, StudentRead, StudentUpdate, ListStudentResponse

# Create the database tables
Base.metadata.create_all(bind=engine)

app = FastAPI()

app.add_middleware(ExceptionHandlingMiddleware)


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


def get_student_repository():
    db = SessionLocal()
    try:
        yield StudentRepository(db)
    finally:
        db.close()


@app.post(
    "/students",
    response_model=StudentRead,
    responses={
        400: {
            "description": "Bad Request",
            "content": {
                "application/json": {
                    "example": {"detail": "Student with mnr already exists"}
                }
            },
        }
    },
)
def create_student(
    student: StudentCreate,
    repository: StudentRepository = Depends(get_student_repository),
):
    repo_student = Student(**student.dict())
    repo_student.id = str(uuid.uuid4())

    repo_student = repository.add(repo_student)
    # that´s dirty
    repository.session.commit()

    student_read = StudentRead(
        id=repo_student.id,
        mnr=repo_student.mnr,
        first_name=repo_student.name,
        last_name=repo_student.last_name,
        email=repo_student.email,
    )

    return student_read


@app.get(
    "/students/{student_id}",
    response_model=StudentRead,
    responses={
        404: {
            "description": "Not found",
            "content": {
                "application/json": {"example": {"detail": "Student not found"}}
            },
        }
    },
)
def get_student(
    student_id: str, repository: StudentRepository = Depends(get_student_repository)
):
    student = repository.get(student_id)
    repository.session.commit()
    if student is None:
        raise ValueError("Student not found")

    student_read = StudentRead(
        id=student.id,
        mnr=student.mnr,
        first_name=student.name,
        last_name=student.last_name,
        email=student.email,
    )

    return student_read


@app.get("/students", response_model=ListStudentResponse)
def list_students(repository: StudentRepository = Depends(get_student_repository)):
    students = repository.list()
    repository.session.commit()
    response_students = [
        StudentRead(
            id=student.id,
            mnr=student.mnr,
            first_name=student.name,
            last_name=student.last_name,
            email=student.email,
        )
        for student in students
    ]

    return ListStudentResponse(students=response_students)


@app.put(
    "/students/{student_id}",
    response_model=StudentRead,
    responses={
        404: {
            "description": "Not found",
            "content": {
                "application/json": {"example": {"detail": "Student not found"}}
            },
        },
        400: {
            "description": "Bad Request",
            "content": {
                "application/json": {
                    "example": {"detail": "Student with mnr already exists"}
                }
            },
        },
    },
)
def update_student(
    student_id: str,
    student_update: StudentUpdate,
    repository: StudentRepository = Depends(get_student_repository),
):
    student = repository.get(student_id)
    if student is None:
        repository.session.rollback()
        raise HTTPException(status_code=404, detail="Student not found")

    if student_update.first_name is not None:
        student.name = student_update.first_name
    if student_update.last_name is not None:
        student.last_name = student_update.last_name
    if student_update.email is not None:
        student.email = student_update.email

    repository.update(student)
    repository.session.commit()

    student_read = StudentRead(
        id=student.id,
        mnr=student.mnr,
        first_name=student.name,
        last_name=student.last_name,
        email=student.email,
    )

    return student_read


@app.delete(
    "/students/{student_id}",
    response_model=Message,
    responses={
        404: {
            "description": "Not found",
            "content": {
                "application/json": {"example": {"detail": "Student not found"}}
            },
        }
    },
)
def delete_student(
    student_id: str, repository: StudentRepository = Depends(get_student_repository)
):
    student = repository.get(student_id)
    if student is None:
        repository.session.rollback()
        raise HTTPException(status_code=404, detail="Student not found")
    repository.delete(student)
    repository.session.commit()

    return Message(message="Student deleted")
