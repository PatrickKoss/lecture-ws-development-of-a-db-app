use std::fmt::{Display, Formatter};
use actix_web::http::StatusCode;
use actix_web::{HttpResponse, ResponseError};
use db::DbError;
use validator::ValidationErrors;
use crate::domain::{ErrorResponse, MessageResponse};

#[derive(Debug)]
pub enum ApiError {
    ValidationError(ValidationErrors),
    DbError(DbError),
    InternalError(anyhow::Error),
}

impl Display for ApiError {
    fn fmt(&self, f: &mut Formatter<'_>) -> std::fmt::Result {
        match self {
            ApiError::ValidationError(e) => write!(f, "Validation error: {:?}", e),
            ApiError::DbError(e) => write!(f, "Database error: {:?}", e),
            ApiError::InternalError(e) => write!(f, "Internal error: {:?}", e),
        }
    }
}

impl ResponseError for ApiError {
    fn status_code(&self) -> StatusCode {
        match *self {
            ApiError::ValidationError(_) => StatusCode::BAD_REQUEST,
            ApiError::DbError(ref e) if matches!(e, DbError::NotFound) => StatusCode::NOT_FOUND,
            ApiError::InternalError(_) => StatusCode::INTERNAL_SERVER_ERROR,
            _ => StatusCode::INTERNAL_SERVER_ERROR,
        }
    }

    fn error_response(&self) -> HttpResponse {
        match self {
            ApiError::ValidationError(ref e) => HttpResponse::BadRequest().json(ErrorResponse {
                message: "Validation failed".to_string(),
                error: format!("{:?}", e),
            }),
            ApiError::DbError(ref e) if matches!(e, DbError::NotFound) => HttpResponse::NotFound().json(MessageResponse {
                message: "Not Found".to_string(),
            }),
            ApiError::InternalError(_) => HttpResponse::InternalServerError().json(ErrorResponse {
                message: "Internal server error".to_string(),
                error: format!("{:?}", self),
            }),
            _ => HttpResponse::InternalServerError().json(ErrorResponse {
                message: "Internal server error".to_string(),
                error: format!("{:?}", self),
            }),
        }
    }
}

impl From<ValidationErrors> for ApiError {
    fn from(e: ValidationErrors) -> Self {
        ApiError::ValidationError(e)
    }
}

impl From<DbError> for ApiError {
    fn from(e: DbError) -> Self {
        ApiError::DbError(e)
    }
}

impl From<anyhow::Error> for ApiError {
    fn from(e: anyhow::Error) -> Self {
        e.downcast::<ValidationErrors>()
            .map(ApiError::ValidationError)
            .or_else(|e| e.downcast::<DbError>().map(ApiError::DbError))
            .unwrap_or_else(|e| ApiError::InternalError(e))
    }
}
