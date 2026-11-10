
package com.library.model;

// Represents a book and stores its basic information and borrowing status.
public class Book {

    private int id;
    private String title;
    private String author;
    private String category;
    private boolean borrowed;

    // Creates a new book that is available for borrowing.
    public Book(int id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.borrowed = false;
    }

    // Getter methods provide access to the book information.
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    // Returns whether the book is currently borrowed.
    public boolean isBorrowed() {
        return borrowed;
    }

    // Updates the borrowing status when a book is borrowed or returned.
    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }
}
