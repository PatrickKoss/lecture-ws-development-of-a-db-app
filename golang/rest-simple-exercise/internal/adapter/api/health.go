package api

import (
	"github.com/PatrickKoss/rest-simple/internal/adapter/api/models"
	"github.com/gofiber/fiber/v2"
)

// Health godoc
// @Summary Health route
// @Description Health route
// @Accept  json
// @Produce  json
// @Success 200 {object} models.Message
// @Router /v1/healthz [get]
// @Tags health
// get route.
func Health(c *fiber.Ctx) error {
	c.Status(fiber.StatusOK)

	return c.JSON(models.Message{
		Message: "healthy",
	})
}
