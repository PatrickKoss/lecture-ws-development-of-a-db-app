package middleware_test

import (
	"github.com/PatrickKoss/rest-simple/internal/adapter/api/middleware"
	"net/http"
	"net/http/httptest"
	"testing"

	metrics_collector "github.com/PatrickKoss/rest-simple/internal/adapter/metrics"
	mock_metrics_collector "github.com/PatrickKoss/rest-simple/internal/adapter/metrics/mock"
	"github.com/gofiber/fiber/v2"
	"github.com/stretchr/testify/assert"
	"go.uber.org/mock/gomock"
)

func TestMetricsMiddleware(t *testing.T) {
	t.Parallel()

	mockCtrl := gomock.NewController(t)
	defer mockCtrl.Finish()

	metricsCollector := mock_metrics_collector.NewMockHttpApiMetrics(mockCtrl)

	// Expectations
	method := http.MethodGet
	path := "/"
	statusCode := http.StatusOK
	contentLength := float64(4)

	metricsCollector.EXPECT().CollectRequest(method, path, statusCode).Times(1)
	metricsCollector.EXPECT().CollectTotalRequests().Times(1)
	metricsCollector.EXPECT().CollectRequestResponseSize(method, path, contentLength).Times(1)
	metricsCollector.EXPECT().CollectRequestDuration(method, path, gomock.Any()).Times(1)

	app := setupTestMetrics(metricsCollector)

	req := httptest.NewRequest(http.MethodGet, "/", nil)
	resp, err := app.Test(req)

	assert.NoError(t, err)
	assert.Equal(t, http.StatusOK, resp.StatusCode)
}

func setupTestMetrics(collector metrics_collector.HttpApiMetrics) *fiber.App {
	app := fiber.New()
	app.Use(middleware.NewMetricsMiddleware(collector))
	app.Get("/", func(c *fiber.Ctx) error {
		return c.SendString("test")
	})

	return app
}

func getTestMockMetricsCollector(ctrl *gomock.Controller) metrics_collector.HttpApiMetrics {
	metricsCollector := mock_metrics_collector.NewMockHttpApiMetrics(ctrl)

	metricsCollector.EXPECT().CollectRequest(gomock.Any(), gomock.Any(), gomock.Any()).AnyTimes()
	metricsCollector.EXPECT().CollectTotalRequests().AnyTimes()
	metricsCollector.EXPECT().CollectRequestResponseSize(gomock.Any(), gomock.Any(), gomock.Any()).AnyTimes()
	metricsCollector.EXPECT().CollectRequestDuration(gomock.Any(), gomock.Any(), gomock.Any()).AnyTimes()
	metricsCollector.EXPECT().Collect400TotalRequests().AnyTimes()
	metricsCollector.EXPECT().Collect500TotalRequests().AnyTimes()

	return metricsCollector
}
