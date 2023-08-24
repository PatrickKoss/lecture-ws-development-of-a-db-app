package main

import (
	"github.com/PatrickKoss/rest-simple/internal/adapter/api"
	"github.com/PatrickKoss/rest-simple/internal/adapter/metrics"
	"github.com/PatrickKoss/rest-simple/internal/adapter/repository"
	"github.com/PatrickKoss/rest-simple/internal/core"
	"github.com/PatrickKoss/rest-simple/internal/service"
	"go.uber.org/zap"
)

func main() {
	studentRepository, err := repository.NewStudentRepository("students.db")
	if err != nil {
		panic(err)
	}
	studentService := service.NewStudentService(studentRepository, core.NewStudentValidator())
	httpMetrics := metrics.NewHttpApiMetrics()

	logger, err := zap.NewProduction()
	if err != nil {
		panic(err)
	}

	app := api.New(logger, httpMetrics, studentService)
	err = app.Listen("8081")
	if err != nil {
		panic(err)
	}
}
