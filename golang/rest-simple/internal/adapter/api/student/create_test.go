package student_test

import (
	"bytes"
	"encoding/json"
	"fmt"
	"net/http"
	"net/http/httptest"
	"testing"
	"time"

	"github.com/PatrickKoss/rest-simple/internal/core"
	"github.com/PatrickKoss/rest-simple/internal/service"
	"github.com/google/uuid"
	"github.com/stretchr/testify/assert"
	"go.uber.org/mock/gomock"
)

func TestCreateStudent(t *testing.T) {
	t.Parallel()

	app, mockService := setup(t)

	testStudent := core.CreateStudent{
		Id:        uuid.New(),
		Name:      "Jane",
		LastName:  "Smith",
		CreatedOn: time.Now().String(),
	}

	mockStudent := core.Student{
		Id:        testStudent.Id,
		Name:      testStudent.Name,
		LastName:  testStudent.LastName,
		CreatedOn: testStudent.CreatedOn,
		Mnr:       "",
	}

	mockService.EXPECT().Create(gomock.Any()).Return(mockStudent, nil)

	body, _ := json.Marshal(testStudent)
	req := httptest.NewRequest(http.MethodPost, "/students", bytes.NewReader(body))
	req.Header.Set("Content-Type", "application/json")

	resp, err := app.Test(req)
	assert.NoError(t, err)
	assert.Equal(t, http.StatusCreated, resp.StatusCode)

	var student core.Student
	err = json.NewDecoder(resp.Body).Decode(&student)
	assert.NoError(t, err)
	assert.Equal(t, mockStudent, student)
}

func TestCreateStudentValidationError(t *testing.T) {
	t.Parallel()

	app, mockService := setup(t)

	testStudent := core.CreateStudent{
		Name:     "",
		LastName: "Smith",
	}

	mockService.EXPECT().Create(gomock.Any()).Return(core.Student{}, service.ValidationError{Err: fmt.Errorf("validation error")})

	body, _ := json.Marshal(testStudent)
	req := httptest.NewRequest(http.MethodPost, "/students", bytes.NewReader(body))
	req.Header.Set("Content-Type", "application/json")

	resp, err := app.Test(req)
	assert.NoError(t, err)
	assert.Equal(t, http.StatusBadRequest, resp.StatusCode)
}
