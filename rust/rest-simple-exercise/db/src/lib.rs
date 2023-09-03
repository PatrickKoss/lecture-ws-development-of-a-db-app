use anyhow::Result;
use async_trait::async_trait;
use sqlx::FromRow;
use sqlx::migrate;
use sqlx::sqlite::SqlitePool;
use validator_derive::Validate;

#[derive(Debug, thiserror::Error)]
pub enum DbError {
    #[error("NotFound")]
    NotFound,

    #[error(transparent)]
    SqlxError(#[from] sqlx::Error),
}

#[derive(Debug, FromRow, PartialEq, Eq)]
pub struct Student {
    // TODO define model
    pub mnr: i64,
}

#[async_trait]
pub trait StudentRepository {
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

#[derive(Validate, Clone, PartialEq, Debug)]
pub struct NewStudent {
    // TODO define model
}

#[derive(Validate, Clone, PartialEq, Debug)]
pub struct UpdateStudent {
    // TODO define model
}

pub struct SqliteStudentRepository {
    pool: SqlitePool,
}

impl SqliteStudentRepository {
    pub async fn new(database_url: &str) -> Result<Self> {
        let pool = SqlitePool::connect(database_url).await?;
        migrate!("./migrations")
            .run(&pool)
            .await?;

        Ok(SqliteStudentRepository { pool })
    }
}

#[async_trait]
impl StudentRepository for SqliteStudentRepository {
    async fn all(&self) -> Result<Vec<Student>> {
        // TODO implement
        Ok(vec![Student { mnr: 123456 }])
    }

    async fn get(&self, id: &str) -> Result<Student> {
        // TODO implement
        Ok(Student { mnr: 123456 })
    }

    async fn create(&self, student: &NewStudent) -> Result<Student> {
        // TODO implement
        Ok(Student { mnr: 123456 })
    }

    async fn update(&self, id: &str, student: &UpdateStudent) -> Result<Student> {
        // TODO implement
        Ok(Student { mnr: 123456 })
    }

    async fn delete(&self, id: &str) -> Result<Student> {
        // TODO implement
        Ok(Student { mnr: 123456 })
    }
}
