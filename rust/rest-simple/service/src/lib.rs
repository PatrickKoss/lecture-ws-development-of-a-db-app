use anyhow::Result;
use async_trait::async_trait;
use db::NewStudent;
use db::Student;
use db::StudentRepository;
use db::UpdateStudent;
#[cfg(test)]
use mockall::{mock, predicate::*};
use validator::Validate;

#[async_trait]
pub trait StudentService: Sync + Send {
    // Get all students
    async fn all(&self) -> Result<Vec<Student>>;
    // Get a specific student by id
    async fn get(&self, id: &str) -> Result<Student>;
    // Create a new student
    async fn create(&self, student: &NewStudent) -> Result<Student>;
    // Update a student's name and last_name
    async fn update(&self, id: &str, student: &UpdateStudent) -> Result<Student>;
    // Delete a student by id
    async fn delete(&self, id: &str) -> Result<Student>;
}

#[derive(Clone)]
pub struct StudentServiceImpl<R: StudentRepository + Send + Sync> {
    repository: R,
}

impl<R: StudentRepository + Send + Sync> StudentServiceImpl<R> {
    pub fn new(repository: R) -> Self {
        StudentServiceImpl { repository }
    }
}

#[async_trait]
impl<R: StudentRepository + Send + Sync> StudentService for StudentServiceImpl<R> {
    async fn all(&self) -> Result<Vec<Student>> {
        self.repository.all().await
    }

    async fn get(&self, id: &str) -> Result<Student> {
        self.repository.get(id).await
    }

    async fn create(&self, student: &NewStudent) -> Result<Student> {
        student.validate()?;

        self.repository.create(student).await
    }

    async fn update(&self, id: &str, student: &UpdateStudent) -> Result<Student> {
        student.validate()?;

        self.repository.update(id, student).await
    }

    async fn delete(&self, id: &str) -> Result<Student> {
        self.repository.delete(id).await
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use futures_util::future::FutureExt;
    use mockall::predicate;

    mock! {
        Repository {}
        #[async_trait]
        impl db::StudentRepository for Repository {
            async fn all(&self) -> Result<Vec<Student>>;
            async fn get(&self, id: &str) -> Result<Student>;
            async fn create(&self, student: &NewStudent) -> Result<Student>;
            async fn update(&self, id: &str, student: &UpdateStudent) -> Result<Student>;
            async fn delete(&self, id: &str) -> Result<Student>;
        }
    }

    #[test]
    fn test_all() {
        let mut mock = MockRepository::new();
        mock.expect_all()
            .times(1)
            .returning(|| Ok(vec![Student {
                id: String::from("14322988-32fe-447c-ac38-06fb6c699b4a"),
                name: String::from("John"),
                mnr: 1,
                created_on: String::from("2021-01-01T00:00:00Z"),
                last_name: String::from("Doe"),
            }]));
        let service = StudentServiceImpl::new(mock);
        let students = service.all().now_or_never().unwrap().unwrap();
        assert_eq!(students.len(), 1);
        assert_eq!(students[0].id, "14322988-32fe-447c-ac38-06fb6c699b4a")
    }

    #[test]
    fn test_get() {
        let mut mock = MockRepository::new();
        let expected_id = "some-id";
        mock.expect_get()
            .with(predicate::eq(expected_id))
            .times(1)
            .returning(|_| Ok(Student {
                id: String::from("some-id"),
                name: String::from("John"),
                mnr: 1,
                created_on: String::from("2021-01-01T00:00:00Z"),
                last_name: String::from("Doe"),
            }));

        let service = StudentServiceImpl::new(mock);
        let student = service.get(expected_id).now_or_never().unwrap().unwrap();
        assert_eq!(student.id, expected_id);
    }

    #[test]
    fn test_create() {
        let mut mock = MockRepository::new();
        let new_student = NewStudent {
            created_on: String::from("2021-01-01T00:00:00Z"),
            id: String::from("new-id"),
            name: String::from("John"),
            last_name: String::from("Doe"),
        };
        let new_student_test = new_student.clone();
        mock.expect_create()
            .with(predicate::eq(new_student_test.clone()))
            .times(1)
            .returning(move |_| Ok(Student {
                id: String::from("new-id"),
                name: new_student_test.name.clone(),
                created_on: new_student_test.created_on.clone(),
                last_name: new_student_test.last_name.clone(),
                mnr: 1,
            }));

        let service = StudentServiceImpl::new(mock);
        let student = service.create(&new_student).now_or_never().unwrap().unwrap();
        assert_eq!(student.id, "new-id");
    }

    #[test]
    fn test_create_with_invalid_student() {
        let mock_repo = MockRepository::new();
        let service = StudentServiceImpl::new(mock_repo);

        let invalid_student = NewStudent {
            id: String::from("some-id"),
            name: String::new(),
            last_name: String::from("Doe"),
            created_on: String::from("2021-01-01T00:00:00Z"),
        };

        let result = service.create(&invalid_student).now_or_never();

        assert!(result.is_some(), "Expected a synchronous result");
        assert!(result.unwrap().is_err(), "Expected an error due to validation");
    }

    #[test]
    fn test_update() {
        let mut mock = MockRepository::new();
        let student_id = "update-id";
        let update_student = UpdateStudent {
            name: String::from("John"),
            last_name: String::from("Doe"),
        };
        let update_student_test = update_student.clone();
        mock.expect_update()
            .with(predicate::eq(student_id), predicate::eq(update_student_test.clone()))
            .times(1)
            .returning(move |_, _| Ok(Student {
                id: String::from("update-id"),
                name: update_student_test.name.clone(),
                created_on: String::from("2021-01-01T00:00:00Z"),
                last_name: update_student_test.last_name.clone(),
                mnr: 1,
            }));

        let service = StudentServiceImpl::new(mock);
        let student = service.update(student_id, &update_student).now_or_never().unwrap().unwrap();
        assert_eq!(student.id, student_id);
    }

    #[test]
    fn test_update_with_invalid_student() {
        let mock_repo = MockRepository::new();
        let service = StudentServiceImpl::new(mock_repo);

        let invalid_student = UpdateStudent {
            name: String::new(),
            last_name: String::from("Doe"),
        };

        let result = service.update("id", &invalid_student).now_or_never();

        assert!(result.is_some(), "Expected a synchronous result");
        assert!(result.unwrap().is_err(), "Expected an error due to validation");
    }

    #[test]
    fn test_delete() {
        let mut mock = MockRepository::new();
        let delete_id = "delete-id";
        mock.expect_delete()
            .with(predicate::eq(delete_id))
            .times(1)
            .returning(|_| Ok(Student {
                id: String::from("delete-id"),
                name: String::from("John"),
                mnr: 1,
                created_on: String::from("2021-01-01T00:00:00Z"),
                last_name: String::from("Doe"),
            }));

        let service = StudentServiceImpl::new(mock);
        let student = service.delete(delete_id).now_or_never().unwrap().unwrap();
        assert_eq!(student.id, delete_id);
    }
}
