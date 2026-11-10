
package com.library.service;

import com.library.model.Book;
import java.util.ArrayList;

// Handles the main book management operations.
public class BookService {

    // Temporarily stores books in memory until the database is connected.
    private ArrayList<Book> books = new ArrayList<>();

    // Adds a book if another book with the same ID does not exist.
    public boolean addBook(Book book) {

        for (Book b : books) {
            if (b.getId() == book.getId()) {
                return false;
            }
        }

        books.add(book);
        return true;
    }

    // Returns all books stored in the library.
    public ArrayList<Book> getBooks() {
        return books;
    }

    // Searches for a book by ID and returns null if it is not found.
    public Book searchBook(int id) {

        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }

        return null;
    }

    // Removes a book only if it exists and is not borrowed.
    public boolean removeBook(int id) {

        Book book = searchBook(id);

        if (book != null && !book.isBorrowed()) {
            return books.remove(book);
        }

        return false;
    }

    // Marks an available book as borrowed.
    public boolean borrowBook(int id) {

        Book book = searchBook(id);

        if (book != null && !book.isBorrowed()) {
            book.setBorrowed(true);
            return true;
        }

        return false;
    }

    // Marks a borrowed book as available again.
    public boolean returnBook(int id) {

        Book book = searchBook(id);

        if (book != null && book.isBorrowed()) {
            book.setBorrowed(false);
            return true;
        }

        return false;
    }
}
