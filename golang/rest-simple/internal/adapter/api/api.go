package api

import (
	"context"
	"encoding/json"
	"fmt"
	"net/http"
	"os"
	"os/signal"
	"syscall"
	"time"

	_ "github.com/PatrickKoss/rest-simple/api"
	"github.com/PatrickKoss/rest-simple/internal/adapter/api/middleware"
	"github.com/PatrickKoss/rest-simple/internal/adapter/api/student"
	"github.com/PatrickKoss/rest-simple/internal/adapter/metrics"
	"github.com/PatrickKoss/rest-simple/internal/service"
	swagger "github.com/arsmn/fiber-swagger/v2"
	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/helmet"
	fiberlogger "github.com/gofiber/fiber/v2/middleware/logger"
	"github.com/gofiber/fiber/v2/middleware/pprof"
	fiberrecover "github.com/gofiber/fiber/v2/middleware/recover"
	"go.uber.org/zap"
)

type Api interface {
	Listen(port string) error
	Test(req *http.Request, msTimeout ...int) (resp *http.Response, err error)
}

type api struct {
	logger *zap.Logger
	app    *fiber.App
}

func (a api) Test(req *http.Request, msTimeout ...int) (resp *http.Response, err error) {
	return a.app.Test(req, msTimeout...)
}

func (a api) Listen(port string) error {
	go func() {
		err := a.app.Listen(fmt.Sprintf(":%s", port))
		if err != nil {
			a.logger.Fatal("Error starting the server", zap.Error(err))
		}
	}()

	sigCh := make(chan os.Signal, 1)
	signal.Notify(sigCh, syscall.SIGHUP, syscall.SIGINT, syscall.SIGTERM, syscall.SIGQUIT)
	sig := <-sigCh

	a.logger.Info(
		"shutting down server due to received signal",
		zap.String("signal", sig.String()),
	)

	ctx, cancel := context.WithTimeout(context.Background(), 30*time.Second)
	err := a.app.ShutdownWithContext(ctx)
	if err != nil {
		a.logger.Error("error shutting down server", zap.String("err", err.Error()))
	}

	cancel()

	return err
}

// New gets new api
// @title Rest Simple
// @version 1.0
// @description This api is an example in golang how to build a simple rest api with database connection.
// @contact.name lecture
// @contact.email lecture@example.com
// @host localhost:8081
// swagger docu.
func New(logger *zap.Logger, middlewareCollector metrics.HttpApiMetrics, studentService service.StudentService) Api {
	app := fiber.New(fiber.Config{
		DisableStartupMessage: true,
		JSONEncoder:           json.Marshal,
		JSONDecoder:           json.Unmarshal,
		ErrorHandler:          middleware.ErrorMappingMiddleware,
	})

	// set swagger route
	app.Get("/swagger/*", swagger.HandlerDefault)

	middleware.RegisterAt(app, "/metrics")
	app.Get("/healthz", Health)

	app.Use(middleware.NewMetricsMiddleware(middlewareCollector))
	app.Use(fiberlogger.New())
	app.Use(pprof.New(pprof.Config{Prefix: "/pprof"}))
	app.Use(fiberrecover.New())
	app.Use(helmet.New())

	app.Get("/students", student.ListStudents(studentService))
	app.Post("/students", student.CreateStudent(studentService))
	app.Put("/students/:id", student.UpdateStudent(studentService))
	app.Delete("/students/:id", student.DeleteStudent(studentService))

	return &api{
		logger: logger,
		app:    app,
	}
}
