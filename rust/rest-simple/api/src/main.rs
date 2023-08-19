use std::{
    error::Error,
    net::Ipv4Addr,
};

use actix_web::{App, HttpServer, middleware::Logger};
use actix_web::web::Data;
use db::{DbError, SqliteStudentRepository};
use service::{StudentService, StudentServiceImpl};
use utoipa::OpenApi;
use utoipa_rapidoc::RapiDoc;
use utoipa_swagger_ui::SwaggerUi;
use validator::ValidationErrors;

mod student;
mod error;
mod domain;

#[actix_web::main]
async fn main() -> Result<(), impl Error> {
    env_logger::init();

    #[derive(OpenApi)]
    #[openapi(
    paths(
    student::list_students,
    student::create_student,
    student::put_students,
    student::delete_student
    ),
    components(
    schemas(student::Student, student::ListStudentsResponse, student::CreateStudentRequest, student::CreateStudentResponse, student::UpdateStudentRequest, student::UpdateStudentResponse, domain::ErrorResponse, domain::MessageResponse)
    ),
    tags(
    (name = "students", description = "Student management endpoints.")
    )
    )]
    struct ApiDoc;

    // Make instance variable of ApiDoc so all worker threads gets the same instance.
    let openapi = ApiDoc::openapi();

    let student_repository = SqliteStudentRepository::new("./students.db").await.expect("Failed to connect to database.");
    let student_service = StudentServiceImpl::new(student_repository);
    let student_service_data = Data::new(Box::new(student_service) as Box<dyn StudentService>);

    HttpServer::new(move || {
        // This factory closure is called on each worker thread independently.
        App::new()
            .wrap(Logger::default())
            .configure(student::configure(student_service_data.clone()))
            .service(
                SwaggerUi::new("/swagger-ui/{_:.*}").url("/api-docs/openapi.json", openapi.clone()),
            )
            .service(RapiDoc::new("/api-docs/openapi.json").path("/rapidoc"))
    })
        .bind((Ipv4Addr::UNSPECIFIED, 8081))?
        .run()
        .await
}

#[derive(Debug)]
pub enum ServiceError {
    Validation(ValidationErrors),
    NotFound,
    DbError(DbError),
    Unknown(anyhow::Error),
}

impl From<ValidationErrors> for ServiceError {
    fn from(err: ValidationErrors) -> Self {
        ServiceError::Validation(err)
    }
}

impl From<DbError> for ServiceError {
    fn from(err: DbError) -> Self {
        ServiceError::DbError(err)
    }
}
