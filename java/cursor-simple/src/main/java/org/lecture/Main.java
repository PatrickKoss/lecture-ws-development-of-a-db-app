package org.lecture;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private LocalDate publishedDate;

    public Book() {}

    public Book(String title, String author, String isbn, LocalDate publishedDate) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
    }

    public Book(int id, String title, String author, String isbn, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public LocalDate getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDate publishedDate) { this.publishedDate = publishedDate; }

    @Override
    public String toString() {
        return String.format("ID: %d | Title: %s | Author: %s | ISBN: %s | Published: %s",
                id, title, author, isbn, publishedDate);
    }
}

public class Main {
    private static final String DATABASE_URL = "jdbc:sqlite:books.db";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeDatabase();
        
        System.out.println("=== Book Management System ===");
        
        while (true) {
            showMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createBook();
                    break;
                case 2:
                    listAllBooks();
                    break;
                case 3:
                    readBook();
                    break;
                case 4:
                    updateBook();
                    break;
                case 5:
                    deleteBook();
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Create Book");
        System.out.println("2. List All Books");
        System.out.println("3. Read Book by ID");
        System.out.println("4. Update Book");
        System.out.println("5. Delete Book");
        System.out.println("6. Exit");
    }

    private static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "author TEXT NOT NULL," +
                "isbn TEXT UNIQUE NOT NULL," +
                "published_date DATE NOT NULL" +
                ")";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createTableSQL);
            System.out.println("Database initialized successfully.");
            
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    private static void createBook() {
        System.out.println("\n--- Create New Book ---");
        
        String title = getStringInput("Enter title: ");
        String author = getStringInput("Enter author: ");
        String isbn = getStringInput("Enter ISBN: ");
        LocalDate publishedDate = getDateInput("Enter published date (YYYY-MM-DD): ");

        String insertSQL = "INSERT INTO books (title, author, isbn, published_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, isbn);
            pstmt.setDate(4, Date.valueOf(publishedDate));
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book created successfully!");
            }
            
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                System.err.println("Error: A book with this ISBN already exists.");
            } else {
                System.err.println("Error creating book: " + e.getMessage());
            }
        }
    }

    private static void listAllBooks() {
        System.out.println("\n--- All Books ---");
        
        String selectSQL = "SELECT id, title, author, isbn, published_date FROM books ORDER BY id";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {
            
            boolean hasBooks = false;
            while (rs.next()) {
                hasBooks = true;
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getDate("published_date").toLocalDate()
                );
                System.out.println(book);
            }
            
            if (!hasBooks) {
                System.out.println("No books found.");
            }
            
        } catch (SQLException e) {
            System.err.println("Error listing books: " + e.getMessage());
        }
    }

    private static void readBook() {
        System.out.println("\n--- Read Book ---");
        
        int id = getIntInput("Enter book ID: ");
        
        String selectSQL = "SELECT id, title, author, isbn, published_date FROM books WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getDate("published_date").toLocalDate()
                    );
                    System.out.println("Book found:");
                    System.out.println(book);
                } else {
                    System.out.println("No book found with ID: " + id);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error reading book: " + e.getMessage());
        }
    }

    private static void updateBook() {
        System.out.println("\n--- Update Book ---");
        
        int id = getIntInput("Enter book ID to update: ");
        
        if (!bookExists(id)) {
            System.out.println("No book found with ID: " + id);
            return;
        }
        
        String title = getStringInput("Enter new title: ");
        String author = getStringInput("Enter new author: ");
        String isbn = getStringInput("Enter new ISBN: ");
        LocalDate publishedDate = getDateInput("Enter new published date (YYYY-MM-DD): ");

        String updateSQL = "UPDATE books SET title = ?, author = ?, isbn = ?, published_date = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, isbn);
            pstmt.setDate(4, Date.valueOf(publishedDate));
            pstmt.setInt(5, id);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book updated successfully!");
            }
            
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                System.err.println("Error: A book with this ISBN already exists.");
            } else {
                System.err.println("Error updating book: " + e.getMessage());
            }
        }
    }

    private static void deleteBook() {
        System.out.println("\n--- Delete Book ---");
        
        int id = getIntInput("Enter book ID to delete: ");
        
        if (!bookExists(id)) {
            System.out.println("No book found with ID: " + id);
            return;
        }
        
        String confirm = getStringInput("Are you sure you want to delete this book? (yes/no): ");
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Delete cancelled.");
            return;
        }

        String deleteSQL = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully!");
            }
            
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
    }

    private static boolean bookExists(int id) {
        String selectSQL = "SELECT 1 FROM books WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking book existence: " + e.getMessage());
            return false;
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Please enter date in YYYY-MM-DD format.");
            }
        }
    }
}
