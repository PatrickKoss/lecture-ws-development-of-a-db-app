package org.lecture;

import java.util.List;
import java.util.Optional;

/**
 * TODO: Implement the StudentRepository interface
 * 
 * This class should provide concrete implementations for all methods defined
 * in the StudentRepository interface.
 * 
 * Remember to:
 * 1. Use the DATABASE_URL constant for database connections
 * 2. Implement all CRUD operations using prepared statements
 * 3. Handle SQLExceptions appropriately
 * 4. Use proper resource management (try-with-resources)
 * 5. Validate business rules (unique constraints)
 */
public class StudentRepositoryImpl implements StudentRepository {
    
    private static final String DATABASE_URL = "jdbc:sqlite:students.db";
    
    @Override
    public void initializeDatabase() {
        // TODO: Implement database initialization
        // Create the students table with proper schema
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public Student createStudent(Student student) {
        // TODO: Implement student creation
        // Use prepared statement to insert student data
        // Return student with generated ID
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public Optional<Student> findStudentById(int id) {
        // TODO: Implement find by ID
        // Use prepared statement to query student by ID
        // Return Optional.empty() if not found
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public List<Student> findAllStudents() {
        // TODO: Implement find all students
        // Use prepared statement to query all students
        // Return empty list if no students found
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public boolean updateStudent(Student student) {
        // TODO: Implement student update
        // Use prepared statement to update student data
        // Return true if successful, false otherwise
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public boolean deleteStudent(int id) {
        // TODO: Implement student deletion
        // Use prepared statement to delete student by ID
        // Return true if successful, false otherwise
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public boolean studentExists(int id) {
        // TODO: Implement existence check
        // Use prepared statement to check if student exists
        // Return true if exists, false otherwise
        throw new UnsupportedOperationException("Not implemented yet");
    }
}