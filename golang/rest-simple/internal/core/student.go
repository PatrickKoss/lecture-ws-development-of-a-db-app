package core

import (
	"github.com/go-playground/validator/v10"
	"github.com/google/uuid"
)

type Student struct {
	// unique identifier of the student
	Id uuid.UUID `json:"id" validate:"required" example:"550e8400-e29b-41d4-a716-446655440000"`
	// first  name of the student
	Name string `json:"name" validate:"required,min=1,max=200" example:"John"`
	// last name of the student
	LastName string `json:"lastName" validate:"required,min=1,max=200" example:"Doe"`
	// date of creation
	CreatedOn string `json:"createdOn" validate:"required" example:"2021-01-01T00:00:00Z"`
	// martikelnumber of the student
	Mnr string `json:"mnr" validate:"required" example:"1234567890"`
}

type CreateStudent struct {
	// unique identifier of the student
	Id uuid.UUID `json:"id" validate:"required" example:"550e8400-e29b-41d4-a716-446655440000"`
	// first  name of the student
	Name string `json:"name" validate:"required,min=1,max=200" example:"John"`
	// last name of the student
	LastName string `json:"lastName" validate:"required,min=1,max=200" example:"Doe"`
	// date of creation
	CreatedOn string `json:"createdOn" validate:"required" example:"2021-01-01T00:00:00Z"`
}

type UpdateStudent struct {
	// first  name of the student
	Name string `json:"name" validate:"required,min=1,max=200" example:"John"`
	// last name of the student
	LastName string `json:"lastName" validate:"required,min=1,max=200" example:"Doe"`
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
