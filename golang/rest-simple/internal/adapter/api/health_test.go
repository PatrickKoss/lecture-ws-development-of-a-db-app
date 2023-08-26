package api_test

import (
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/PatrickKoss/rest-simple/internal/adapter/api"
	mock_service "github.com/PatrickKoss/rest-simple/internal/service/mock"
	"github.com/gofiber/fiber/v2"
	"github.com/stretchr/testify/assert"
	"go.uber.org/mock/gomock"
	"go.uber.org/zap"
)

func TestHealth(t *testing.T) {
	t.Parallel()

	ctrl := gomock.NewController(t)

	mockLogger := zap.NewNop()
	mockMetricsCollector := getTestMockMetricsCollector(ctrl)
	mockStudentService := mock_service.NewMockStudentService(ctrl)

	app := api.New(mockLogger, mockMetricsCollector, mockStudentService)

	req := httptest.NewRequest(http.MethodGet, "/healthz", nil)
	req.Header.Set(fiber.HeaderContentType, fiber.MIMEApplicationJSON)

	resp, err := app.Test(req, -1)
	assert.NoError(t, err)
	assert.Equal(t, http.StatusOK, resp.StatusCode)
}
