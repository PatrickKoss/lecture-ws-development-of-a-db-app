package org.lecture;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Student entities in the database.
 * 
 * This interface defines the contract for CRUD operations on Student objects.
 * Students should implement this interface with proper database interactions
 * using prepared statements for security and performance.
 * 
 * Implementation requirements:
 * - Use SQLite database with JDBC
 * - All database operations must use prepared statements
 * - Handle SQL exceptions appropriately
 * - Ensure proper resource management (use try-with-resources)
 * - Validate constraints (unique email, student number)
 */
public interface StudentRepository {
    
    /**
     * Initialize the database and create the students table if it doesn't exist.
     * 
     * Table schema should include:
     * - id (INTEGER PRIMARY KEY AUTOINCREMENT)
     * - first_name (TEXT NOT NULL)
     * - last_name (TEXT NOT NULL) 
     * - email (TEXT UNIQUE NOT NULL)
     * - student_number (TEXT UNIQUE NOT NULL)
     * - enrollment_date (DATE NOT NULL)
     */
    void initializeDatabase();
    
    /**
     * Create a new student in the database.
     * 
     * @param student The student to create (id will be auto-generated)
     * @return The created student with generated id, or null if creation failed
     */
    Student createStudent(Student student);
    
    /**
     * Find a student by their database ID.
     * 
     * @param id The student's database ID
     * @return Optional containing the student if found, empty otherwise
     */
    Optional<Student> findStudentById(int id);
    
    /**
     * Retrieve all students from the database.
     * 
     * @return List of all students, ordered by ID
     */
    List<Student> findAllStudents();
    
    /**
     * Update an existing student's information.
     * 
     * @param student The student with updated information (must have valid id)
     * @return true if update was successful, false otherwise
     */
    boolean updateStudent(Student student);
    
    /**
     * Delete a student from the database.
     * 
     * @param id The ID of the student to delete
     * @return true if deletion was successful, false otherwise
     */
    boolean deleteStudent(int id);
    
    /**
     * Check if a student exists with the given ID.
     * 
     * @param id The student ID to check
     * @return true if student exists, false otherwise
     */
    boolean studentExists(int id);
}