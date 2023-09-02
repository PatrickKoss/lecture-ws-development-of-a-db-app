package repository

import (
	"context"
	"database/sql"
	"fmt"
	"strconv"

	"github.com/PatrickKoss/rest-simple/internal/adapter/repository/sqlboiler/models"
	"github.com/PatrickKoss/rest-simple/internal/core"
	"github.com/friendsofgo/errors"
	"github.com/google/uuid"
	_ "github.com/mattn/go-sqlite3"
	"github.com/volatiletech/sqlboiler/v4/boil"
	"github.com/volatiletech/sqlboiler/v4/queries"
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
	students, err := models.Students().All(context.Background(), s.db)
	if err != nil {
		return nil, err
	}

	// Convert to core.Student slice
	coreStudents := make([]core.Student, len(students))
	for i, student := range students {
		id, _ := uuid.Parse(student.ID)
		coreStudents[i] = core.Student{
			Id:        id,
			Mnr:       strconv.FormatInt(student.MNR, 10),
			Name:      student.Name,
			LastName:  student.LastName,
			CreatedOn: student.CreatedOn,
		}
	}

	return coreStudents, nil
}

func (s studentRepository) Create(student core.CreateStudent) (core.Student, error) {
	newStudent := models.Student{
		ID:        student.Id.String(),
		Name:      student.Name,
		LastName:  student.LastName,
		CreatedOn: student.CreatedOn,
	}

	err := newStudent.Insert(context.Background(), s.db, boil.Infer())
	if err != nil {
		return core.Student{}, err
	}

	id, _ := uuid.Parse(newStudent.ID)

	return core.Student{
		Id:        id,
		Mnr:       strconv.FormatInt(newStudent.MNR, 10),
		Name:      newStudent.Name,
		LastName:  newStudent.LastName,
		CreatedOn: newStudent.CreatedOn,
	}, nil
}

func (s studentRepository) Update(student core.UpdateStudent, id uuid.UUID) (core.Student, error) {
	var dbStudent models.Student
	err := queries.Raw("UPDATE student SET name = ?, last_name = ? WHERE id = ? RETURNING *", student.Name, student.LastName, id.String()).Bind(context.Background(), s.db, &dbStudent)
	if err != nil {
		if errors.Is(err, sql.ErrNoRows) {
			return core.Student{}, ErrNotFound
		}

		return core.Student{}, err
	}

	id, _ = uuid.Parse(dbStudent.ID)

	return core.Student{
		Id:        id,
		Mnr:       strconv.FormatInt(dbStudent.MNR, 10),
		Name:      dbStudent.Name,
		LastName:  dbStudent.LastName,
		CreatedOn: dbStudent.CreatedOn,
	}, nil
}

func (s studentRepository) Delete(id uuid.UUID) error {
	_, err := queries.Raw("DELETE FROM student WHERE id = ?", id.String()).Exec(s.db)
	if err != nil {
		if errors.Is(err, sql.ErrNoRows) {
			return ErrNotFound
		}

		return err
	}

	return nil
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
