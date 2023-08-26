package student_test

import (
	"bytes"
	"encoding/json"
	"fmt"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/PatrickKoss/rest-simple/internal/adapter/repository"
	"github.com/PatrickKoss/rest-simple/internal/core"
	"github.com/PatrickKoss/rest-simple/internal/service"
	"github.com/google/uuid"
	"github.com/stretchr/testify/assert"
)

func TestUpdateStudent(t *testing.T) {
	t.Parallel()

	app, mockService := setup(t)

	id := uuid.New()
	testStudent := core.UpdateStudent{
		Name:     "Jane",
		LastName: "Doe",
	}

	mockService.EXPECT().Update(testStudent, id).Return(core.Student{}, nil)

	body, _ := json.Marshal(testStudent)
	req := httptest.NewRequest(http.MethodPut, "/students/"+id.String(), bytes.NewReader(body))
	req.Header.Set("Content-Type", "application/json")

	resp, err := app.Test(req)
	assert.NoError(t, err)
	assert.Equal(t, http.StatusOK, resp.StatusCode)
}

func TestUpdateStudentValidationError(t *testing.T) {
	t.Parallel()

	app, mockService := setup(t)

	id := uuid.New()
	testStudent := core.UpdateStudent{
		Name:     "",
		LastName: "Doe",
	}

	mockService.EXPECT().Update(testStudent, id).Return(core.Student{}, service.ValidationError{Err: fmt.Errorf("validation error")})

	body, _ := json.Marshal(testStudent)
	req := httptest.NewRequest(http.MethodPut, "/students/"+id.String(), bytes.NewReader(body))
	req.Header.Set("Content-Type", "application/json")

	resp, err := app.Test(req)
	assert.NoError(t, err)
	assert.Equal(t, http.StatusBadRequest, resp.StatusCode)
}

func TestUpdateStudentNotFound(t *testing.T) {
	t.Parallel()

	app, mockService := setup(t)

	id := uuid.New()
	testStudent := core.UpdateStudent{
		Name:     "",
		LastName: "Doe",
	}

	mockService.EXPECT().Update(testStudent, id).Return(core.Student{}, repository.ErrNotFound)

	body, _ := json.Marshal(testStudent)
	req := httptest.NewRequest(http.MethodPut, "/students/"+id.String(), bytes.NewReader(body))
	req.Header.Set("Content-Type", "application/json")

	resp, err := app.Test(req)
	assert.NoError(t, err)
	assert.Equal(t, http.StatusNotFound, resp.StatusCode)
}
