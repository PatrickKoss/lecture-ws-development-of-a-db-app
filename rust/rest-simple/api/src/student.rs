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
        let mut mock_service = MockService::new();

        mock_service.expect_all()
            .times(1)
            .returning(|| Ok(vec![db::Student {
                id: String::from("14322988-32fe-447c-ac38-06fb6c699b4a"),
                name: String::from("John"),
                mnr: 1,
                created_on: String::from("2021-01-01T00:00:00Z"),
                last_name: String::from("Doe"),
            }]));

        let student_service_data = Data::new(Box::new(mock_service) as Box<dyn StudentService>);

        let mut app = test::init_service(
            App::new().configure(configure(student_service_data.clone()))
        ).await;

        let req = test::TestRequest::get().uri("/students").to_request();
        let resp = test::call_service(&mut app, req).await;

        assert!(resp.status().is_success());
    }

    #[actix_web::test]
    async fn test_create_student() {
        let mut mock_service = MockService::new();

        let new_student = db::NewStudent {
            created_on: String::from("2021-01-01T00:00:00Z"),
            id: String::from("new-id"),
            name: String::from("John"),
            last_name: String::from("Doe"),
        };
        let new_student_test = new_student.clone();
        mock_service.expect_create()
            .times(1)
            .returning(move |_| Ok(db::Student {
                id: String::from("new-id"),
                name: new_student_test.name.clone(),
                created_on: new_student_test.created_on.clone(),
                last_name: new_student_test.last_name.clone(),
                mnr: 1,
            }));

        let student_service_data = Data::new(Box::new(mock_service) as Box<dyn StudentService>);

        let mut app = test::init_service(
            App::new().configure(configure(student_service_data.clone()))
        ).await;

        let student = CreateStudentRequest {
            name: "John".to_string(),
            last_name: "Doe".to_string(),
        };

        let req = test::TestRequest::post()
            .uri("/students")
            .set_json(&student)
            .to_request();

        let resp = test::call_service(&mut app, req).await;

        assert!(resp.status().is_success());

        let body = test::read_body(resp).await;

        let returned_student: CreateStudentResponse = serde_json::from_slice(&body).unwrap();

        let expected_student = db::Student {
            id: String::from("new-id"),
            name: String::from("John"),
            last_name: String::from("Doe"),
            created_on: String::from("2021-01-01T00:00:00Z"),
            mnr: 1,
        };
        assert_eq!(returned_student.student.id, expected_student.id);
        assert_eq!(returned_student.student.name, expected_student.name);
        assert_eq!(returned_student.student.last_name, expected_student.last_name);
        assert_eq!(returned_student.student.created_on, expected_student.created_on);
    }

    #[actix_web::test]
    async fn test_update_student() {
        let mut mock_service = MockService::new();

        let student_id = "some-id";
        let update_request = UpdateStudentRequest {
            name: "UpdatedJohn".to_string(),
            last_name: "UpdatedDoe".to_string(),
        };
        let updated_student = db::UpdateStudent {
            name: update_request.name.clone(),
            last_name: update_request.last_name.clone(),
        };
        let updated_student_test = updated_student.clone();
        let updated_student_test_predicate = updated_student.clone();
        mock_service.expect_update()
            .with(predicate::eq(student_id), predicate::eq(updated_student_test_predicate))
            .times(1)
            .returning(move |_, _| Ok(db::Student {
                id: student_id.clone().to_string(),
                name: updated_student_test.name.clone(),
                last_name: updated_student_test.last_name.clone(),
                created_on: String::from("2021-01-01T00:00:00Z"),
                mnr: 1,
            }));

        let student_service_data = Data::new(Box::new(mock_service) as Box<dyn StudentService>);

        let mut app = test::init_service(
            App::new().configure(configure(student_service_data.clone()))
        ).await;

        let req = test::TestRequest::put()
            .uri(&format!("/students/{}", student_id))
            .set_json(&update_request)
            .to_request();

        let resp = test::call_service(&mut app, req).await;

        assert!(resp.status().is_success());

        let body = test::read_body(resp).await;
        let returned_student: UpdateStudentResponse = serde_json::from_slice(&body).unwrap();

        assert_eq!(returned_student.student.name, update_request.name);
        assert_eq!(returned_student.student.last_name, update_request.last_name);
    }

    #[actix_web::test]
    async fn test_delete_student() {
        let mut mock_service = MockService::new();

        let student_id = "some-id";
        mock_service.expect_delete()
            .with(predicate::eq(student_id))
            .times(1)
            .returning(|_| Ok(db::Student {
                id: student_id.to_string(),
                name: String::from("John"),
                last_name: String::from("Doe"),
                created_on: String::from("2021-01-01T00:00:00Z"),
                mnr: 1,
            }));

        let student_service_data = Data::new(Box::new(mock_service) as Box<dyn StudentService>);

        let mut app = test::init_service(
            App::new().configure(configure(student_service_data.clone()))
        ).await;

        let req = test::TestRequest::delete()
            .uri(&format!("/students/{}", student_id))
            .to_request();

        let resp = test::call_service(&mut app, req).await;

        assert!(resp.status().is_success());
        assert_eq!(resp.status(), 204);
    }

    #[actix_web::test]
    async fn test_delete_student_not_found() {
        let mut mock_service = MockService::new();

        // Set up the mock to return an error of type db::NotFound
        mock_service.expect_delete()
            .times(1)
            .returning(|_| Err(anyhow::anyhow!(db::DbError::NotFound)));

        let student_service_data = Data::new(Box::new(mock_service) as Box<dyn StudentService>);

        let mut app = test::init_service(
            App::new().configure(configure(student_service_data.clone()))
        ).await;

        let req = test::TestRequest::delete().uri("/students/some_id").to_request();
        let resp = test::call_service(&mut app, req).await;

        assert_eq!(resp.status(), 404);
    }
}
