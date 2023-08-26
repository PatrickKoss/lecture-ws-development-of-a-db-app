package student

import (
	"github.com/PatrickKoss/rest-simple/internal/service"
	"github.com/gofiber/fiber/v2"
	"github.com/google/uuid"
)

// DeleteStudent godoc
// @Summary delete an existing student
// @Description delete an existing student
// @Accept  json
// @Produce  json
// @Param student path string true "student id"
// @Success 204
// @Failure 404 {object} models.ErrorMessage
// @Router /students/{id} [delete]
// @Tags student
// delete route.
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
