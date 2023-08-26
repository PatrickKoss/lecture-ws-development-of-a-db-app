package middleware

import (
	"errors"


	"github.com/PatrickKoss/rest-simple/internal/adapter/api/models"
	"github.com/PatrickKoss/rest-simple/internal/adapter/repository"
	"github.com/PatrickKoss/rest-simple/internal/service"
	"github.com/gofiber/fiber/v2"
)

func ErrorMappingMiddleware(c *fiber.Ctx, err error) error {
	switch {
	case errors.Is(err, repository.ErrNotFound):
		return c.Status(fiber.StatusNotFound).JSON(models.ErrorMessage{
			Message: "not found",
			Error:   err.Error(),
		})
	case errors.Is(err, service.ValidationError{}):
		return c.Status(fiber.StatusBadRequest).JSON(models.ErrorMessage{
			Message: "validation error",
			Error:   err.Error(),
		})
	default:
		return c.Status(fiber.StatusInternalServerError).JSON(models.ErrorMessage{
			Message: "internal server error",
			Error:   err.Error(),
		})
	}
}
