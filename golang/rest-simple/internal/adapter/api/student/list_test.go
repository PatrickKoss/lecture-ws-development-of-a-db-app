package student_test

import (
	"encoding/json"
	"github.com/PatrickKoss/rest-simple/internal/adapter/api/middleware"
	"github.com/PatrickKoss/rest-simple/internal/adapter/api/student"
	"github.com/PatrickKoss/rest-simple/internal/core"
	service_mock "github.com/PatrickKoss/rest-simple/internal/service/mock"
	"github.com/gofiber/fiber/v2"
	"github.com/google/uuid"
	"github.com/stretchr/testify/assert"
	"go.uber.org/mock/gomock"
	"net/http"
	"net/http/httptest"
	"testing"
	"time"
)

func TestListStudents(t *testing.T) {
	t.Parallel()

	app, mockService := setup(t)

	// Define test data
	mockStudents := []core.Student{{
		Id:        uuid.New(),
		Name:      "John",
		LastName:  "Doe",
		CreatedOn: time.Now().String(),
		Mnr:       "1",
	}}

	mockService.EXPECT().All().Return(mockStudents, nil)

	req := httptest.NewRequest(http.MethodGet, "/students", nil)

	resp, err := app.Test(req)
	if err != nil {
		return
	}
	assert.Equal(t, http.StatusOK, resp.StatusCode)

	var students student.ListStudentResponse
	err = json.NewDecoder(resp.Body).Decode(&students)
	assert.NoError(t, err)
	assert.Equal(t, mockStudents[0], students.Students[0])
}

func setup(t *testing.T) (*fiber.App, *service_mock.MockStudentService) {
	t.Helper()

	app := fiber.New(fiber.Config{
		ErrorHandler: middleware.ErrorMappingMiddleware,
	})

	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	mockService := service_mock.NewMockStudentService(ctrl)

	app.Get("/students", student.ListStudents(mockService))
	app.Post("/students", student.CreateStudent(mockService))
	app.Put("/students/:id", student.UpdateStudent(mockService))
	app.Delete("/students/:id", student.DeleteStudent(mockService))

	return app, mockService
}
