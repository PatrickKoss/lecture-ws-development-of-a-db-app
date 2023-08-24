package student

import (
	"github.com/PatrickKoss/rest-simple/internal/core"
	"github.com/PatrickKoss/rest-simple/internal/service"
	"github.com/gofiber/fiber/v2"
	"github.com/google/uuid"
	"time"
)

func CreateStudent(studentService service.StudentService) fiber.Handler {
	return func(c *fiber.Ctx) error {
		var student core.UpdateStudent
		if err := c.BodyParser(&student); err != nil {
			return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
				"error": "Failed to parse request",
			})
		}

		createStudent := core.CreateStudent{
			Id:        uuid.New(),
			Name:      student.Name,
			LastName:  student.LastName,
			CreatedOn: time.Now().String(),
		}

		newStudent, err := studentService.Create(createStudent)
		if err != nil {
			return err
		}

		return c.JSON(newStudent)
	}
}
