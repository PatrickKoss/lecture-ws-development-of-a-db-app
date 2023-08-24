package student

import (
	"github.com/PatrickKoss/rest-simple/internal/service"
	"github.com/gofiber/fiber/v2"
	"github.com/google/uuid"
)

func DeleteStudent(studentService service.StudentService) fiber.Handler {
	return func(c *fiber.Ctx) error {
		id, err := uuid.Parse(c.Params("id"))
		if err != nil {
			return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
				"error": "Invalid UUID format",
			})
		}

		err = studentService.Delete(id)
		if err != nil {
			return err
		}
		return c.SendStatus(fiber.StatusNoContent) // 204 No Content
	}
}
