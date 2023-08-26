package student

import (
	"github.com/PatrickKoss/rest-simple/internal/core"
	"github.com/PatrickKoss/rest-simple/internal/service"
	"github.com/gofiber/fiber/v2"
	"github.com/google/uuid"
)

// UpdateStudent godoc
// @Summary update an existing student
// @Description update an existing student
// @Accept  json
// @Produce  json
// @Param student path string true "student id"
// @Param data body core.UpdateStudent true "student to update"
// @Success 200 {object} core.Student
// @Failure 400 {object} models.ErrorMessage
// @Failure 404 {object} models.ErrorMessage
// @Router /students/{id} [put]
// @Tags student
// update route.
func UpdateStudent(studentService service.StudentService) fiber.Handler {
	return func(c *fiber.Ctx) error {
		id, err := uuid.Parse(c.Params("id"))
		if err != nil {
			return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
				"error": "Invalid UUID format",
			})
		}

		var student core.UpdateStudent
		if err = c.BodyParser(&student); err != nil {
			return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
				"error": "Failed to parse request",
			})
		}

		updatedStudent, err := studentService.Update(student, id)
		if err != nil {
			return err
		}

		return c.JSON(updatedStudent)
	}
}
