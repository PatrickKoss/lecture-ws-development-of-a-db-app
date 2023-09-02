package core

import (
	"github.com/go-playground/validator/v10"
)

type Student struct {
	// add something meaningful here
}

type CreateStudent struct {
}

type UpdateStudent struct {
}

type studentValidator struct {
	validate *validator.Validate
}

func (s studentValidator) ValidateCreateStudent(student CreateStudent) error {
	panic("implement me")
}

func (s studentValidator) ValidateUpdateStudent(student UpdateStudent) error {
	panic("implement me")
}

type StudentValidator interface {
	ValidateStudent(student Student) error
	ValidateCreateStudent(student CreateStudent) error
	ValidateUpdateStudent(student UpdateStudent) error
}

func (s studentValidator) ValidateStudent(student Student) error {
	return s.validate.Struct(student)
}

func NewStudentValidator() StudentValidator {
	return studentValidator{validate: validator.New()}
}
