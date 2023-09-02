package middleware

import (
	"github.com/gofiber/fiber/v2"
)

func ErrorMappingMiddleware(c *fiber.Ctx, err error) error {
	panic("implement me")
}
