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
    pub id: String,
    pub mnr: i64,
    pub name: String,
    pub last_name: String,
    pub created_on: String,
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
    pub id: String,
    #[validate(length(min = 1, max = 200))]
    pub name: String,
    #[validate(length(min = 1, max = 200))]
    pub last_name: String,
    pub created_on: String,
}

#[derive(Validate, Clone, PartialEq, Debug)]
pub struct UpdateStudent {
    #[validate(length(min = 1, max = 200))]
    pub name: String,
    #[validate(length(min = 1, max = 200))]
    pub last_name: String,
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
        let students = sqlx::query_as!(Student, "SELECT * FROM student")
            .fetch_all(&self.pool)
            .await?;

        Ok(students)
    }

    async fn get(&self, id: &str) -> Result<Student> {
        let student = sqlx::query_as!(Student, "SELECT * FROM student WHERE id = ?", id)
            .fetch_one(&self.pool)
            .await?;

        Ok(student)
    }

    async fn create(&self, student: &NewStudent) -> Result<Student> {
        let new_student = sqlx::query_as!(
        Student,
        "INSERT INTO student (id, name, last_name, created_on) VALUES ($1, $2, $3, $4) RETURNING *",
        student.id, student.name, student.last_name, student.created_on
    )
            .fetch_one(&self.pool)
            .await?;

        Ok(new_student)
    }

    async fn update(&self, id: &str, student: &UpdateStudent) -> Result<Student> {
        let updated_student = sqlx::query_as!(
            Student,
            "UPDATE student SET name = $1, last_name = $2 WHERE id = $3 RETURNING *",
            student.name, student.last_name, id
        )
            .fetch_one(&self.pool)
            .await
            .map_err(|e| {
                match e {
                    sqlx::Error::RowNotFound => DbError::NotFound,
                    _ => DbError::SqlxError(e),
                }
            })?;

        Ok(updated_student)
    }

    async fn delete(&self, id: &str) -> Result<Student> {
        let deleted_student = sqlx::query_as!(Student, "DELETE FROM student WHERE id = ? RETURNING *", id)
            .fetch_one(&self.pool)
            .await
            .map_err(|e| {
                match e {
                    sqlx::Error::RowNotFound => DbError::NotFound,
                    _ => DbError::SqlxError(e),
                }
            })?;

        Ok(deleted_student)
    }
}
