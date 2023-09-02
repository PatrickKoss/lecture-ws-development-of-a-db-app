package models

type Message struct {
	// human-readable message
	Message string `json:"message" example:"success" validate:"required"`
}

type ErrorMessage struct {
	// human readable error message
	Message string `json:"message" example:"failed" validate:"required"`
	// concrete error
	Error string `json:"error" example:"failed to unmarshal" validate:"required"`
}
