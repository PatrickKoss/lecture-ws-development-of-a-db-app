use async_trait::async_trait;
use db::StudentRepository;
use db::Student;
use db::NewStudent;
use db::UpdateStudent;
use anyhow::Result;
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
