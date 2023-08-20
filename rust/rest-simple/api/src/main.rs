use std::{
    error::Error,
    net::Ipv4Addr,
};

use actix_web::{App, HttpResponse, HttpServer, middleware, web};
use actix_web::web::Data;
use db::SqliteStudentRepository;
use service::{StudentService, StudentServiceImpl};
use utoipa::OpenApi;
use utoipa_rapidoc::RapiDoc;
use utoipa_swagger_ui::SwaggerUi;

use crate::domain::MessageResponse;

mod student;
mod error;
mod domain;

#[actix_web::main]
async fn main() -> Result<(), impl Error> {
    env_logger::init_from_env(env_logger::Env::new().default_filter_or("info"));

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
            .wrap(middleware::Logger::default())
            .configure(student::configure(student_service_data.clone()))
            .service(
                SwaggerUi::new("/swagger-ui/{_:.*}").url("/api-docs/openapi.json", openapi.clone()),
            )
            .service(RapiDoc::new("/api-docs/openapi.json").path("/rapidoc"))
            .default_service(web::route().to(not_found))
    })
        .bind((Ipv4Addr::UNSPECIFIED, 8081))?
        .run()
        .await
}

async fn not_found() -> HttpResponse {
    HttpResponse::NotFound().json(MessageResponse {
        message: "not found".to_string(),
    })
}
