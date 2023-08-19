use std::{
    error::Error,
    net::Ipv4Addr,
};

use actix_web::{
    App,
    dev::{Service, Transform},
    HttpServer, middleware::Logger,
};
use actix_web::web::Data;
use utoipa::{
    OpenApi,
};
use utoipa_rapidoc::RapiDoc;
use utoipa_swagger_ui::SwaggerUi;
use service::{StudentService, StudentServiceImpl};
use db::SqliteStudentRepository;

mod students;

#[actix_web::main]
async fn main() -> Result<(), impl Error> {
    env_logger::init();

    #[derive(OpenApi)]
    #[openapi(
    paths(
    students::list_students,
    students::create_student,
    students::put_students,
    students::delete_student
    ),
    components(
    schemas(students::Student, students::ListStudentsResponse, students::CreateStudentRequest, students::CreateStudentResponse, students::UpdateStudentRequest, students::UpdateStudentResponse, students::ErrorResponse)
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
            .configure(students::configure(student_service_data.clone()))
            .service(
                SwaggerUi::new("/swagger-ui/{_:.*}").url("/api-docs/openapi.json", openapi.clone()),
            )
            .service(RapiDoc::new("/api-docs/openapi.json").path("/rapidoc"))
    })
        .bind((Ipv4Addr::UNSPECIFIED, 8081))?
        .run()
        .await
}
