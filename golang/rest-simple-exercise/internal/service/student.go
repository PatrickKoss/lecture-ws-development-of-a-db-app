package service

import (
	"errors"

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
	var validationError ValidationError
	ok := errors.As(target, &validationError)

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
	// TODO implement me
	panic("implement me")
}

func (s studentService) Create(student core.CreateStudent) (core.Student, error) {
	// TODO implement me
	panic("implement me")
}

func (s studentService) Update(student core.UpdateStudent, id uuid.UUID) (core.Student, error) {
	// TODO implement me
	panic("implement me")
}

func (s studentService) Delete(id uuid.UUID) error {
	// TODO implement me
	panic("implement me")
}

func NewStudentService(repo repository.StudentRepository, studentValidator core.StudentValidator) StudentService {
	return studentService{repo: repo, studentValidator: studentValidator}
}
