use actix_web::{delete, get, HttpResponse, post, put, web::{Data, Path, ServiceConfig}};
use actix_web::web::Json;
use db::UpdateStudent;
use serde::{Deserialize, Serialize};
use service::StudentService;
use utoipa::ToSchema;
use uuid::Uuid;
use crate::error::ApiError;
use crate::domain::{ErrorResponse, MessageResponse};
#[cfg(test)]
use mockall::{mock, predicate::*};

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
    // TODO implement
}

#[derive(Serialize, Deserialize, Clone, ToSchema)]
pub(super) struct ListStudentsResponse {
    students: Vec<Student>,
}

#[derive(Serialize, Deserialize, Clone, ToSchema)]
#[serde(rename_all = "camelCase")]
pub(super) struct CreateStudentRequest {
    // TODO implement
}

#[derive(Serialize, Deserialize, Clone, ToSchema)]
pub(super) struct CreateStudentResponse {
    student: Student,
}

#[derive(Serialize, Deserialize, Clone, ToSchema)]
#[serde(rename_all = "camelCase")]
pub(super) struct UpdateStudentRequest {
    // TODO implement
}

#[derive(Serialize, Deserialize, Clone, ToSchema)]
pub(super) struct UpdateStudentResponse {
    student: Student,
}

impl From<db::Student> for Student {
    fn from(db_student: db::Student) -> Self {
        // TODO implement
        Self {
        }
    }
}

#[get("/students")]
pub(super) async fn list_students(student_service: Data<Box<dyn StudentService>>) -> Result<HttpResponse, ApiError> {
    // TODO implement
    Ok(HttpResponse::from(HttpResponse::Ok()))
}

#[post("/students")]
pub(super) async fn create_student(student_service: Data<Box<dyn StudentService>>, create_student: Json<CreateStudentRequest>) -> Result<HttpResponse, ApiError> {
    // TODO implement
    Ok(HttpResponse::from(HttpResponse::Ok()))
}

#[put("/students/{id}")]
pub(super) async fn put_students(id: Path<String>, student_service: Data<Box<dyn StudentService>>, update_student: Json<UpdateStudentRequest>) -> Result<HttpResponse, ApiError> {
    // TODO implement
    Ok(HttpResponse::from(HttpResponse::Ok()))
}

#[delete("/students/{id}")]
pub(super) async fn delete_student(id: Path<String>, student_service: Data<Box<dyn StudentService>>) -> Result<HttpResponse, ApiError> {
    // TODO implement
    Ok(HttpResponse::from(HttpResponse::Ok()))
}

#[cfg(test)]
mod tests {
    use actix_web::test;
    use super::*;
    use anyhow::Result;
    use async_trait::async_trait;
    use actix_web::App;
    use mockall::predicate;

    mock! {
        Service {}
        #[async_trait]
        impl service::StudentService for Service {
            async fn all(&self) -> Result<Vec<db::Student>>;
            async fn get(&self, id: &str) -> Result<db::Student>;
            async fn create(&self, student: &db::NewStudent) -> Result<db::Student>;
            async fn update(&self, id: &str, student: &db::UpdateStudent) -> Result<db::Student>;
            async fn delete(&self, id: &str) -> Result<db::Student>;
        }
    }

    #[actix_web::test]
    async fn test_list_students() {
        // TODO implement
    }

    #[actix_web::test]
    async fn test_create_student() {
        // TODO implement
    }

    #[actix_web::test]
    async fn test_update_student() {
        // TODO implement
    }

    #[actix_web::test]
    async fn test_delete_student() {
        // TODO implement
    }

    #[actix_web::test]
    async fn test_delete_student_not_found() {
        // TODO implement
    }
}
