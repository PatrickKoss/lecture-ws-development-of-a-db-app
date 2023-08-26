package student

import (
	"time"


	"github.com/PatrickKoss/rest-simple/internal/adapter/api/models"
	"github.com/PatrickKoss/rest-simple/internal/core"
	"github.com/PatrickKoss/rest-simple/internal/service"
	"github.com/gofiber/fiber/v2"
	"github.com/google/uuid"
)

// CreateStudent godoc
// @Summary create a new student
// @Description create a new student
// @Accept  json
// @Produce  json
// @Param data body core.UpdateStudent true "student to create"
// @Success 201 {object} core.Student
// @Failure 400 {object} models.ErrorMessage
// @Router /students [post]
// @Tags student
// create route.
func CreateStudent(studentService service.StudentService) fiber.Handler {
	return func(c *fiber.Ctx) error {
		var student core.UpdateStudent
		if err := c.BodyParser(&student); err != nil {
			return c.Status(fiber.StatusBadRequest).JSON(models.ErrorMessage{
				Message: "failed to unmarshal student",
				Error:   err.Error(),
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

		return c.Status(fiber.StatusCreated).JSON(newStudent)
	}
}
