package student

import (
	"github.com/PatrickKoss/rest-simple/internal/core"
	"github.com/PatrickKoss/rest-simple/internal/service"
	"github.com/gofiber/fiber/v2"
)

type ListStudentResponse struct {
	Students []core.Student `json:"students"`
}

func ListStudents(studentService service.StudentService) fiber.Handler {
	return func(c *fiber.Ctx) error {
		students, err := studentService.All()
		if err != nil {
			return err
		}

		return c.JSON(ListStudentResponse{Students: students})
	}
}
