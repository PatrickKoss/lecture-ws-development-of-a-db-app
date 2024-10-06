from fastapi import FastAPI, Depends

from adapters.api.error_middleware import ExceptionHandlingMiddleware
from adapters.repository.database import SessionLocal, engine
from adapters.repository.models import Base
from core.message import Message
from core.student import StudentCreate, StudentRead, StudentUpdate, ListStudentResponse
from services.student_service import StudentService

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


def get_student_service():
    db = SessionLocal()
    try:
        yield StudentService(db)
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
    student: StudentCreate, service: StudentService = Depends(get_student_service)
):
    created_student = service.create_student(student)
    student_read = StudentRead(
        id=created_student.id,
        mnr=created_student.mnr,
        first_name=created_student.name,
        last_name=created_student.last_name,
        email=created_student.email,
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
    student_id: str, service: StudentService = Depends(get_student_service)
):
    student = service.get_student(student_id)
    student_read = StudentRead(
        id=student.id,
        mnr=student.mnr,
        first_name=student.name,
        last_name=student.last_name,
        email=student.email,
    )

    return student_read


@app.get("/students", response_model=ListStudentResponse)
def list_students(service: StudentService = Depends(get_student_service)):
    students = service.list_students()
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
    service: StudentService = Depends(get_student_service),
):
    updated_student = service.update_student(student_id, student_update)

    student_read = StudentRead(
        id=updated_student.id,
        mnr=updated_student.mnr,
        first_name=updated_student.name,
        last_name=updated_student.last_name,
        email=updated_student.email,
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
    student_id: str, service: StudentService = Depends(get_student_service)
):
    service.delete_student(student_id)
    return Message(message="Student deleted")
