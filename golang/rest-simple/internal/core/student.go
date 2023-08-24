package core

import (
	"github.com/go-playground/validator/v10"
	"github.com/google/uuid"
)

type Student struct {
	Id        uuid.UUID `json:"id" validate:"required"`
	Name      string    `json:"name" validate:"required,min=1,max=200"`
	LastName  string    `json:"lastName" validate:"required,min=1,max=200"`
	CreatedOn string    `json:"createdOn" validate:"required"`
	Mnr       string    `json:"mnr" validate:"required"`
}

type CreateStudent struct {
	Id        uuid.UUID `json:"id" validate:"required"`
	Name      string    `json:"name" validate:"required,min=1,max=200"`
	LastName  string    `json:"lastName" validate:"required,min=1,max=200"`
	CreatedOn string    `json:"createdOn" validate:"required"`
}

type UpdateStudent struct {
	Name     string `json:"name" validate:"required,min=1,max=200"`
	LastName string `json:"lastName" validate:"required,min=1,max=200"`
}

type studentValidator struct {
	validate *validator.Validate
}

func (s studentValidator) ValidateCreateStudent(student CreateStudent) error {
	return s.validate.Struct(student)
}

func (s studentValidator) ValidateUpdateStudent(student UpdateStudent) error {
	return s.validate.Struct(student)
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
