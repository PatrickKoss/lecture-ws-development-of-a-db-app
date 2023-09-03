use anyhow::Result;
use async_trait::async_trait;
#[cfg(test)]
use mockall::{mock, predicate::*};
use validator::Validate;

use db::NewStudent;
use db::Student;
use db::StudentRepository;
use db::UpdateStudent;

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
        Ok(vec![Student { mnr: 123456 }])
    }

    async fn get(&self, id: &str) -> Result<Student> {
        Ok(Student { mnr: 123456 })
    }

    async fn create(&self, student: &NewStudent) -> Result<Student> {
        Ok(Student { mnr: 123456 })
    }

    async fn update(&self, id: &str, student: &UpdateStudent) -> Result<Student> {
        Ok(Student { mnr: 123456 })
    }

    async fn delete(&self, id: &str) -> Result<Student> {
        Ok(Student { mnr: 123456 })
    }
}

#[cfg(test)]
mod tests {
    use futures_util::future::FutureExt;

    use super::*;

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
        // TODO implement
    }

    #[test]
    fn test_get() {
        // TODO implement
    }

    #[test]
    fn test_create() {
        // TODO implement
    }

    #[test]
    fn test_create_with_invalid_student() {
        // TODO implement
    }

    #[test]
    fn test_update() {
        // TODO implement
    }

    #[test]
    fn test_update_with_invalid_student() {
        // TODO implement
    }

    #[test]
    fn test_delete() {
        // TODO implement
    }
}
