use actix_web::{delete, get, HttpResponse, post, put, web::{Data, Path, ServiceConfig}};
use actix_web::web::Json;
use db::UpdateStudent;
use serde::{Deserialize, Serialize};
use service::StudentService;
use utoipa::ToSchema;
use uuid::Uuid;
use crate::error::ApiError;
use crate::domain::{ErrorResponse, MessageResponse};

pub(super) fn configure(student_service: Data<Box<dyn StudentService>>) -> impl FnOnce(&mut ServiceConfig) {
    |config: &mut ServiceConfig| {
        config
            .app_data(student_service)
            .service(list_students)
            .service(create_student)
            .service(put_students)
            .service(delete_student);
    }
}

#[derive(Serialize, Deserialize, Clone, ToSchema)]
#[serde(rename_all = "camelCase")]
pub(super) struct Student {
    /// Unique id
    #[schema(example = "14322988-32fe-447c-ac38-06fb6c699b4a")]
    id: String,
    /// Name of the student
    #[schema(example = "John")]
    name: String,
    /// Matrikelnumber of the student
    #[schema(example = 1)]
    mnr: i64,
    /// Date of creation
    #[schema(example = "2021-01-01T00:00:00Z")]
    created_on: String,
    /// Last name of the student
    #[schema(example = "Doe")]
    last_name: String,
}

#[derive(Serialize, Deserialize, Clone, ToSchema)]
pub(super) struct ListStudentsResponse {
    students: Vec<Student>,
}

#[derive(Serialize, Deserialize, Clone, ToSchema)]
#[serde(rename_all = "camelCase")]
pub(super) struct CreateStudentRequest {
    /// Name of the student
    #[schema(example = "John")]
    name: String,
    /// Last name of the student
    #[schema(example = "Doe")]
    last_name: String,
}

#[derive(Serialize, Deserialize, Clone, ToSchema)]
pub(super) struct CreateStudentResponse {
    student: Student,
}

#[derive(Serialize, Deserialize, Clone, ToSchema)]
#[serde(rename_all = "camelCase")]
pub(super) struct UpdateStudentRequest {
    /// Name of the student
    #[schema(example = "John")]
    name: String,
    /// Last name of the student
    #[schema(example = "Doe")]
    last_name: String,
}

#[derive(Serialize, Deserialize, Clone, ToSchema)]
pub(super) struct UpdateStudentResponse {
    student: Student,
}

impl From<db::Student> for Student {
    fn from(db_student: db::Student) -> Self {
        Self {
            id: db_student.id,
            name: db_student.name,
            mnr: db_student.mnr,
            created_on: db_student.created_on.to_string(),
            last_name: db_student.last_name,
        }
    }
}

#[utoipa::path(
responses(
(status = 200, description = "List students", body = ListStudentsResponse, example = json ! (ListStudentsResponse{students: vec ! [Student{id: String::from("14322988-32fe-447c-ac38-06fb6c699b4a"), name: String::from("John"), mnr: 1, created_on: String::from("2021-01-01T00:00:00Z"), last_name: String::from("Doe")}]})),
)
)]
#[get("/students")]
pub(super) async fn list_students(student_service: Data<Box<dyn StudentService>>) -> Result<HttpResponse, ApiError> {
    let db_students = student_service.all().await?;
    let api_students: Vec<Student> = db_students.into_iter().map(Student::from).collect();

    Ok(HttpResponse::Ok().json(ListStudentsResponse { students: api_students }))
}

#[utoipa::path(
request_body = CreateStudentRequest,
responses(
(status = 201, description = "Student created successfully", body = CreateStudentResponse, example = json ! (CreateStudentResponse{student: Student{id: String::from("14322988-32fe-447c-ac38-06fb6c699b4a"), name: String::from("John"), mnr: 1, created_on: String::from("2021-01-01T00:00:00Z"), last_name: String::from("Doe")}})),
(status = 400, description = "Student not valid", body = ErrorResponse, example = json ! (ErrorResponse{message: String::from("body not valid"), error: String::from("name too long")})),
)
)]
#[post("/students")]
pub(super) async fn create_student(student_service: Data<Box<dyn StudentService>>, create_student: Json<CreateStudentRequest>) -> Result<HttpResponse, ApiError> {
    let new_student = db::NewStudent {
        id: Uuid::new_v4().to_string(),
        name: create_student.name.clone(),
        last_name: create_student.last_name.clone(),
        created_on: chrono::offset::Utc::now().naive_utc().to_string(),
    };

    let db_student = student_service.create(&new_student).await?;
    let api_student = Student::from(db_student);

    Ok(HttpResponse::Ok().json(CreateStudentResponse { student: api_student }))
}

#[utoipa::path(
responses(
(status = 200, description = "Student updated successfully", body = UpdateStudentResponse, example = json ! (UpdateStudentResponse{student: Student{id: String::from("14322988-32fe-447c-ac38-06fb6c699b4a"), name: String::from("John"), mnr: 1, created_on: String::from("2021-01-01T00:00:00Z"), last_name: String::from("Doe")}})),
(status = 400, description = "Student not valid", body = ErrorResponse, example = json ! (ErrorResponse{message: String::from("body not valid"), error: String::from("name too long")})),
(status = 404, description = "Student not found by id", body = ErrorResponse, example = json ! (MessageResponse{message: String::from("student not found")}))
),
params(
("id", description = "Unique id")
),
)]
#[put("/students/{id}")]
pub(super) async fn put_students(id: Path<String>, student_service: Data<Box<dyn StudentService>>, update_student: Json<UpdateStudentRequest>) -> Result<HttpResponse, ApiError> {
    let db_student = student_service
        .update(
            id.as_str(),
            &UpdateStudent {
                last_name: update_student.last_name.clone(),
                name: update_student.name.clone(),
            },
        )
        .await?;
    let api_student = Student::from(db_student);

    Ok(HttpResponse::Ok().json(UpdateStudentResponse { student: api_student }))
}

#[utoipa::path(
responses(
(status = 204, description = "Student deleted successfully"),
(status = 404, description = "Student not found by id", body = ErrorResponse, example = json ! (MessageResponse{message: String::from("student not found")}))
),
params(
("id", description = "Unique id")
),
)]
#[delete("/students/{id}")]
pub(super) async fn delete_student(id: Path<String>, student_service: Data<Box<dyn StudentService>>) -> Result<HttpResponse, ApiError> {
    student_service.delete(id.as_str()).await?;

    Ok(HttpResponse::NoContent().finish())
}
