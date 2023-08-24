package service

import (
	"go.uber.org/mock/gomock"
	"testing"
	"time"

	repository_mock "github.com/PatrickKoss/rest-simple/internal/adapter/repository/mock"
	"github.com/PatrickKoss/rest-simple/internal/core"
	"github.com/google/uuid"
	"github.com/stretchr/testify/assert"
)

func TestStudentService_All(t *testing.T) {
	ctrl := gomock.NewController(t)
	mockRepo := repository_mock.NewMockStudentRepository(ctrl)
	validator := core.NewStudentValidator()
	svc := NewStudentService(mockRepo, validator)

	expectedStudents := []core.Student{
		{Id: uuid.New(), Name: "John", LastName: "Doe"},
	}

	mockRepo.EXPECT().All().Return(expectedStudents, nil)

	students, err := svc.All()
	assert.NoError(t, err)
	assert.Equal(t, expectedStudents, students)
}

func TestStudentService_Create_ValidStudent(t *testing.T) {
	ctrl := gomock.NewController(t)
	mockRepo := repository_mock.NewMockStudentRepository(ctrl)
	validator := core.NewStudentValidator()
	svc := NewStudentService(mockRepo, validator)

	createStudent := core.CreateStudent{Name: "John", LastName: "Doe", Id: uuid.New(), CreatedOn: time.Now().String()}
	expectedStudent := core.Student{Id: uuid.New(), Name: "John", LastName: "Doe"}

	mockRepo.EXPECT().Create(createStudent).Return(expectedStudent, nil)

	student, err := svc.Create(createStudent)
	assert.NoError(t, err)
	assert.Equal(t, expectedStudent, student)
}

func TestStudentService_Create_InvalidStudent(t *testing.T) {
	ctrl := gomock.NewController(t)
	mockRepo := repository_mock.NewMockStudentRepository(ctrl)
	validator := core.NewStudentValidator()
	svc := NewStudentService(mockRepo, validator)

	createStudent := core.CreateStudent{Name: "John", LastName: ""}

	_, err := svc.Create(createStudent)

	assert.Error(t, err)
}

func TestStudentService_Update_ValidStudent(t *testing.T) {
	ctrl := gomock.NewController(t)
	mockRepo := repository_mock.NewMockStudentRepository(ctrl)
	validator := core.NewStudentValidator()
	svc := NewStudentService(mockRepo, validator)

	updateStudent := core.UpdateStudent{Name: "Jane", LastName: "Doe"}
	id := uuid.New()

	expectedStudent := core.Student{Id: id, Name: "Jane", LastName: "Doe"}

	mockRepo.EXPECT().Update(updateStudent, id).Return(expectedStudent, nil)

	student, err := svc.Update(updateStudent, id)
	assert.NoError(t, err)
	assert.Equal(t, expectedStudent, student)
}

func TestStudentService_Update_InvalidStudent(t *testing.T) {
	ctrl := gomock.NewController(t)
	mockRepo := repository_mock.NewMockStudentRepository(ctrl)
	validator := core.NewStudentValidator()
	svc := NewStudentService(mockRepo, validator)

	updateStudent := core.UpdateStudent{Name: "Jane", LastName: ""}
	id := uuid.New()

	_, err := svc.Update(updateStudent, id)
	assert.Error(t, err)
}

func TestStudentService_Delete(t *testing.T) {
	ctrl := gomock.NewController(t)
	mockRepo := repository_mock.NewMockStudentRepository(ctrl)
	validator := core.NewStudentValidator()
	svc := NewStudentService(mockRepo, validator)
	id := uuid.New()

	mockRepo.EXPECT().Delete(id).Return(nil)

	err := svc.Delete(id)
	assert.NoError(t, err)
}
