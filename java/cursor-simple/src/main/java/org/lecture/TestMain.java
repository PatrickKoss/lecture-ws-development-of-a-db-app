package org.lecture;

import java.sql.*;
import java.time.LocalDate;

public class TestMain {
    private static final String DATABASE_URL = "jdbc:sqlite:test_books.db";

    public static void main(String[] args) {
        System.out.println("=== Testing Book CRUD Operations ===");
        
        try {
            // Initialize database
            initializeDatabase();
            
            // Test Create
            testCreate();
            
            // Test Read/List
            testReadAndList();
            
            // Test Update
            testUpdate();
            
            // Test Delete
            testDelete();
            
            System.out.println("All tests completed successfully!");
            
        } catch (SQLException e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }
    
    private static void initializeDatabase() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "author TEXT NOT NULL," +
                "isbn TEXT UNIQUE NOT NULL," +
                "published_date DATE NOT NULL" +
                ")";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement()) {
            
            stmt.execute("DROP TABLE IF EXISTS books");
            stmt.execute(createTableSQL);
            System.out.println("✓ Database initialized");
        }
    }
    
    private static void testCreate() throws SQLException {
        String insertSQL = "INSERT INTO books (title, author, isbn, published_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            pstmt.setString(1, "Clean Code");
            pstmt.setString(2, "Robert C. Martin");
            pstmt.setString(3, "978-0132350884");
            pstmt.setDate(4, Date.valueOf(LocalDate.of(2008, 8, 1)));
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✓ Create: Book created successfully");
            }
        }
    }
    
    private static void testReadAndList() throws SQLException {
        String selectSQL = "SELECT id, title, author, isbn, published_date FROM books ORDER BY id";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {
            
            System.out.println("✓ List: Books in database:");
            while (rs.next()) {
                System.out.printf("  ID: %d | Title: %s | Author: %s | ISBN: %s | Published: %s%n",
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getDate("published_date").toLocalDate()
                );
            }
        }
        
        // Test read specific book
        String selectByIdSQL = "SELECT id, title, author, isbn, published_date FROM books WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(selectByIdSQL)) {
            
            pstmt.setInt(1, 1);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("✓ Read: Found book with ID 1");
                }
            }
        }
    }
    
    private static void testUpdate() throws SQLException {
        String updateSQL = "UPDATE books SET title = ?, author = ?, isbn = ?, published_date = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            
            pstmt.setString(1, "Clean Code: Updated Edition");
            pstmt.setString(2, "Robert C. Martin");
            pstmt.setString(3, "978-0132350884");
            pstmt.setDate(4, Date.valueOf(LocalDate.of(2009, 8, 1)));
            pstmt.setInt(5, 1);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✓ Update: Book updated successfully");
            }
        }
    }
    
    private static void testDelete() throws SQLException {
        String deleteSQL = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            
            pstmt.setInt(1, 1);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✓ Delete: Book deleted successfully");
            }
        }
    }
}