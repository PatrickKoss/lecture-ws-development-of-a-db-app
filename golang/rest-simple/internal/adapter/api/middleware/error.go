package middleware

import (
	"errors"
	"github.com/PatrickKoss/rest-simple/internal/adapter/repository"
	"github.com/PatrickKoss/rest-simple/internal/service"
	"github.com/gofiber/fiber/v2"
)

func ErrorMappingMiddleware(c *fiber.Ctx, err error) error {
	switch {
	case errors.Is(err, repository.ErrNotFound):
		return c.Status(fiber.StatusNotFound).JSON(fiber.Map{
			"message": "not found",
			"error":   err.Error(),
		})
	case errors.Is(err, service.ValidationError{}):
		return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"message": "not valid",
			"error":   err.Error(),
		})
	default:
		return c.Status(fiber.StatusInternalServerError).JSON(fiber.Map{
			"message": "internal server error",
			"error":   err.Error(),
		})
	}
}
