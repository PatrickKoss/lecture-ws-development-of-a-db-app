package student_test

import (
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/PatrickKoss/rest-simple/internal/adapter/repository"
	"github.com/google/uuid"
	"github.com/stretchr/testify/assert"
)

func TestDeleteStudent(t *testing.T) {
	t.Parallel()

	app, mockService := setup(t)

	id := uuid.New()
	mockService.EXPECT().Delete(id).Return(nil)

	req := httptest.NewRequest(http.MethodDelete, "/students/"+id.String(), nil)

	resp, err := app.Test(req)
	assert.NoError(t, err)
	assert.Equal(t, http.StatusNoContent, resp.StatusCode)
}

func TestDeleteStudentNotFound(t *testing.T) {
	t.Parallel()

	app, mockService := setup(t)

	id := uuid.New()
	mockService.EXPECT().Delete(id).Return(repository.ErrNotFound)

	req := httptest.NewRequest(http.MethodDelete, "/students/"+id.String(), nil)

	resp, err := app.Test(req)
	assert.NoError(t, err)
	assert.Equal(t, http.StatusNotFound, resp.StatusCode)
}
