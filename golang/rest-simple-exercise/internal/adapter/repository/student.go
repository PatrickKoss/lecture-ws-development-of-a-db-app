package repository

import (
	"database/sql"
	"fmt"
	"github.com/PatrickKoss/rest-simple/internal/core"
	"github.com/google/uuid"
	_ "github.com/mattn/go-sqlite3"
	"github.com/volatiletech/sqlboiler/v4/boil"
)

var ErrNotFound = fmt.Errorf("not found")

//go:generate mockgen -source=student.go -destination=./mock/student.go . StudentRepository
type StudentRepository interface {
	All() ([]core.Student, error)
	Create(student core.CreateStudent) (core.Student, error)
	Update(student core.UpdateStudent, id uuid.UUID) (core.Student, error)
	Delete(id uuid.UUID) error
}

type studentRepository struct {
	db *sql.DB
}

func (s studentRepository) All() ([]core.Student, error) {
	// TODO implement me
	panic("implement me")
}

func (s studentRepository) Create(student core.CreateStudent) (core.Student, error) {
	// TODO implement me
	panic("implement me")
}

func (s studentRepository) Update(student core.UpdateStudent, id uuid.UUID) (core.Student, error) {
	// TODO implement me
	panic("implement me")
}

func (s studentRepository) Delete(id uuid.UUID) error {
	// TODO implement me
	panic("implement me")
}

func NewStudentRepository(connectionString string) (StudentRepository, error) {
	// Connect to the database
	db, err := sql.Open("sqlite3", connectionString)
	if err != nil {
		return nil, err
	}

	boil.SetDB(db)

	return studentRepository{db: db}, nil
}
