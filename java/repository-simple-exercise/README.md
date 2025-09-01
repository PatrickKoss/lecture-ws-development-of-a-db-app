# Student Management System - Repository Pattern Exercise

## Overview

This exercise demonstrates the Repository pattern in Java using SQLite database interactions. You will implement a complete CRUD (Create, Read, Update, Delete) system for managing student records with proper database operations using prepared statements.

## Learning Objectives

After completing this exercise, you will understand:

- **Repository Pattern**: Separation of business logic from data access logic
- **Database Operations**: CRUD operations using JDBC and SQLite
- **Prepared Statements**: Secure database interactions preventing SQL injection
- **Resource Management**: Proper use of try-with-resources for database connections
- **Error Handling**: Managing SQLExceptions and constraint violations
- **CLI Development**: Building interactive command-line applications

## Exercise Description

You are provided with a skeleton Student Management System that includes:

- ✅ **Student Model**: Complete entity class with all required fields
- ✅ **CLI Interface**: Full command-line interface with menu system
- ✅ **Repository Interface**: Complete interface defining all required operations
- ❌ **Repository Implementation**: **THIS IS WHAT YOU NEED TO IMPLEMENT**

### Student Entity

The `Student` class contains the following fields:

- `id` (int) - Auto-generated primary key
- `firstName` (String) - Student's first name
- `lastName` (String) - Student's last name
- `email` (String) - Unique email address
- `studentNumber` (String) - Unique student identifier
- `enrollmentDate` (LocalDate) - Date of enrollment

### Database Schema

You need to create a SQLite table with the following structure:

```sql
CREATE TABLE IF NOT EXISTS students (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    student_number TEXT UNIQUE NOT NULL,
    enrollment_date DATE NOT NULL
);
```

## Your Task

### 1. Implement StudentRepositoryImpl

Complete the implementation of `StudentRepositoryImpl.java` by implementing all methods defined in the `StudentRepository` interface:

#### Required Methods

1. **`initializeDatabase()`**

   - Create database connection to `students.db`
   - Create the students table if it doesn't exist
   - Handle any SQLExceptions appropriately

2. **`createStudent(Student student)`**

   - Insert new student into database using prepared statement
   - Return the student with generated ID
   - Handle unique constraint violations (email, student number)

3. **`findStudentById(int id)`**

   - Query student by ID using prepared statement
   - Return `Optional<Student>` (empty if not found)

4. **`findAllStudents()`**

   - Query all students ordered by ID
   - Return `List<Student>` (empty list if none found)

5. **`updateStudent(Student student)`**

   - Update existing student using prepared statement
   - Return boolean indicating success
   - Handle unique constraint violations

6. **`deleteStudent(int id)`**

   - Delete student by ID using prepared statement
   - Return boolean indicating success

7. **`studentExists(int id)`**
   - Check if student exists with given ID
   - Return boolean result

### 2. Enable CLI Functionality

Once you've implemented the repository:

1. **Uncomment the repository initialization** in `Main.java`:

   ```java
   studentRepository = new StudentRepositoryImpl();
   studentRepository.initializeDatabase();
   ```

2. **Uncomment all the TODO blocks** in the CLI methods to enable full functionality

## Implementation Requirements

### ⚠️ Important Guidelines

1. **Use Prepared Statements**: All database operations MUST use prepared statements for security
2. **Resource Management**: Use try-with-resources for all database connections
3. **Error Handling**: Catch and handle SQLExceptions appropriately
4. **Constraint Handling**: Handle unique constraint violations gracefully
5. **Null Safety**: Handle null inputs and edge cases
6. **Database URL**: Use `"jdbc:sqlite:students.db"` as defined in the constant

### Example Implementation Pattern

```java
@Override
public Student createStudent(Student student) {
    String insertSQL = "INSERT INTO students (first_name, last_name, email, student_number, enrollment_date) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL);
         PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

        pstmt.setString(1, student.getFirstName());
        pstmt.setString(2, student.getLastName());
        pstmt.setString(3, student.getEmail());
        pstmt.setString(4, student.getStudentNumber());
        pstmt.setDate(5, Date.valueOf(student.getEnrollmentDate()));

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    student.setId(generatedKeys.getInt(1));
                    return student;
                }
            }
        }
        return null;

    } catch (SQLException e) {
        if (e.getMessage().contains("UNIQUE constraint failed")) {
            // Handle unique constraint violation
            System.err.println("Error: Student with this email or student number already exists.");
        } else {
            System.err.println("Error creating student: " + e.getMessage());
        }
        return null;
    }
}
```

## Project Setup

### Prerequisites

- Java 21 or higher
- Gradle (wrapper included)

### Getting Started

1. **Clone/Navigate to the exercise directory**:

   ```bash
   cd repository-simple-exercise
   ```

2. **Build the project**:

   ```bash
   ./gradlew build
   ```

3. **Run the application**:

   ```bash
   ./gradlew run
   ```

### Testing Your Implementation

1. **Manual Testing**: Use the CLI to test all CRUD operations
2. **Database Verification**: Check that `students.db` is created and contains your data
3. **Error Testing**: Test edge cases like duplicate emails/student numbers

### Example Usage

```bash
=== Student Management System ===
Initializing database...
Database initialized successfully.

--- Student Management Menu ---
1. Create Student
2. List All Students
3. Find Student by ID
4. Update Student
5. Delete Student
6. Exit

Enter your choice: 1

--- Create New Student ---
Enter first name: John
Enter last name: Doe
Enter email: john.doe@university.edu
Enter student number: STU001
Enter enrollment date (YYYY-MM-DD): 2024-01-15
Student created successfully!
ID: 1 | Name: John Doe | Email: john.doe@university.edu | Student#: STU001 | Enrolled: 2024-01-15
```

## Success Criteria

Your implementation is complete when:

- ✅ All repository methods are implemented without throwing `UnsupportedOperationException`
- ✅ Database table is created successfully
- ✅ All CRUD operations work through the CLI
- ✅ Unique constraints are properly handled
- ✅ Prepared statements are used for all database operations
- ✅ Resources are properly managed (no connection leaks)
- ✅ Error handling provides meaningful feedback to users

## Common Pitfalls to Avoid

1. **SQL Injection**: Don't concatenate user input into SQL strings
2. **Resource Leaks**: Always use try-with-resources for connections
3. **Constraint Violations**: Handle unique constraint failures gracefully
4. **Null Handling**: Check for null inputs and database results
5. **Date Conversion**: Properly convert between `LocalDate` and `java.sql.Date`

## Advanced Challenges (Optional)

Once you've completed the basic implementation, try these enhancements:

1. **Search Functionality**: Add methods to search students by name or email
2. **Data Validation**: Add input validation (email format, date ranges)
3. **Batch Operations**: Implement methods to create/update multiple students
4. **Connection Pooling**: Implement basic connection pooling
5. **Transactions**: Add transaction support for multi-operation scenarios

## Architecture Benefits

This exercise demonstrates the Repository pattern benefits:

- **Separation of Concerns**: Business logic separated from data access
- **Testability**: Repository can be mocked for unit testing
- **Maintainability**: Database changes isolated to repository layer
- **Flexibility**: Easy to switch database implementations
- **Reusability**: Repository can be used by multiple services

Good luck with your implementation! 🚀
