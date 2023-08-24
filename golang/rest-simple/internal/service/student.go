package service

import (
	"github.com/PatrickKoss/rest-simple/internal/adapter/repository"
	"github.com/PatrickKoss/rest-simple/internal/core"
	"github.com/google/uuid"
)

type ValidationError struct {
	Err error
}

func (e ValidationError) Error() string {
	return e.Err.Error()
}

func (e ValidationError) Is(target error) bool {
	_, ok := target.(ValidationError)

	return ok
}

//go:generate mockgen -source=student.go -destination=./mock/student.go . StudentService
type StudentService interface {
	All() ([]core.Student, error)
	Create(student core.CreateStudent) (core.Student, error)
	Update(student core.UpdateStudent, id uuid.UUID) (core.Student, error)
	Delete(id uuid.UUID) error
}

type studentService struct {
	repo             repository.StudentRepository
	studentValidator core.StudentValidator
}

func (s studentService) All() ([]core.Student, error) {
	return s.repo.All()
}

func (s studentService) Create(student core.CreateStudent) (core.Student, error) {
	err := s.studentValidator.ValidateCreateStudent(student)
	if err != nil {
		return core.Student{}, ValidationError{Err: err}
	}

	return s.repo.Create(student)
}

func (s studentService) Update(student core.UpdateStudent, id uuid.UUID) (core.Student, error) {
	err := s.studentValidator.ValidateUpdateStudent(student)
	if err != nil {
		return core.Student{}, ValidationError{Err: err}
	}

	return s.repo.Update(student, id)
}

func (s studentService) Delete(id uuid.UUID) error {
	return s.repo.Delete(id)
}

func NewStudentService(repo repository.StudentRepository, studentValidator core.StudentValidator) StudentService {
	return studentService{repo: repo, studentValidator: studentValidator}
}
