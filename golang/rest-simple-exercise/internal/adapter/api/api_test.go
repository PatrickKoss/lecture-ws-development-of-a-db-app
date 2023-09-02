package api_test

import (
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/PatrickKoss/rest-simple/internal/adapter/api"
	metrics_collector "github.com/PatrickKoss/rest-simple/internal/adapter/metrics"
	mock_metrics_collector "github.com/PatrickKoss/rest-simple/internal/adapter/metrics/mock"
	mock_service "github.com/PatrickKoss/rest-simple/internal/service/mock"
	"github.com/stretchr/testify/assert"
	"go.uber.org/mock/gomock"
	"go.uber.org/zap"
)

func TestApi(t *testing.T) {
	t.Parallel()

	ctrl := gomock.NewController(t)
	t.Cleanup(ctrl.Finish)

	mockLogger := zap.NewNop()
	mockService := mock_service.NewMockStudentService(ctrl)
	mockMetricsCollector := getTestMockMetricsCollector(ctrl)

	app := api.New(mockLogger, mockMetricsCollector, mockService)

	t.Run("Test", func(t *testing.T) {
		t.Parallel()

		req := httptest.NewRequest(http.MethodGet, "/healthz", nil)

		resp, err := app.Test(req)
		assert.NoError(t, err)
		assert.Equal(t, http.StatusOK, resp.StatusCode)
	})
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
