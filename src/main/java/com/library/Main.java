
package com.library;

import com.library.model.Book;
import com.library.service.BookService;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Creates the JavaFX interface and connects user actions to BookService.
public class Main extends Application {

    private BookService bookService = new BookService();

    @Override
    public void start(Stage stage) {

        // Create the input fields for book information.
        Label title = new Label("Library Management System");

        TextField idField = new TextField();
        idField.setPromptText("Book ID");

        TextField nameField = new TextField();
        nameField.setPromptText("Book Title");

        TextField authorField = new TextField();
        authorField.setPromptText("Author");

        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");

        // Buttons for the main library operations.
        Button addButton = new Button("Add Book");
        Button removeButton = new Button("Remove Book");
        Button searchButton = new Button("Search Book");
        Button borrowButton = new Button("Borrow Book");
        Button returnButton = new Button("Return Book");
        Button showButton = new Button("Show All");

        ListView<String> bookList = new ListView<>();

        // Add a new book after checking the entered information.
        addButton.setOnAction(e -> {

            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                String author = authorField.getText().trim();
                String category = categoryField.getText().trim();

                if (id <= 0 || name.isEmpty() ||
                        author.isEmpty() || category.isEmpty()) {
                    showMessage("Please enter valid book information.");
                    return;
                }

                Book book = new Book(id, name, author, category);

                if (bookService.addBook(book)) {
                    updateList(bookList);
                    showMessage("Book added successfully!");

                    idField.clear();
                    nameField.clear();
                    authorField.clear();
                    categoryField.clear();

                } else {
                    showMessage("Book ID already exists!");
                }

            } catch (NumberFormatException ex) {
                showMessage("Please enter a valid Book ID!");
            }
        });

        // Remove a book using its ID if it is not borrowed.
        removeButton.setOnAction(e -> {

            try {
                int id = Integer.parseInt(idField.getText().trim());

                if (bookService.removeBook(id)) {
                    updateList(bookList);
                    showMessage("Book removed!");
                } else {
                    showMessage("Book not found or currently borrowed!");
                }

            } catch (NumberFormatException ex) {
                showMessage("Enter a valid Book ID!");
            }
        });

        // Search for a book and display its information.
        searchButton.setOnAction(e -> {

            try {
                int id = Integer.parseInt(idField.getText().trim());
                Book book = bookService.searchBook(id);

                bookList.getItems().clear();

                if (book != null) {
                    bookList.getItems().add(bookInfo(book));
                } else {
                    showMessage("Book not found!");
                }

            } catch (NumberFormatException ex) {
                showMessage("Enter a valid Book ID!");
            }
        });

        // Borrow a book if it is currently available.
        borrowButton.setOnAction(e -> {

            try {
                int id = Integer.parseInt(idField.getText().trim());

                if (bookService.borrowBook(id)) {
                    updateList(bookList);
                    showMessage("Book borrowed!");
                } else {
                    showMessage("Book unavailable or not found!");
                }

            } catch (NumberFormatException ex) {
                showMessage("Enter a valid Book ID!");
            }
        });

        // Return a borrowed book to the library.
        returnButton.setOnAction(e -> {

            try {
                int id = Integer.parseInt(idField.getText().trim());

                if (bookService.returnBook(id)) {
                    updateList(bookList);
                    showMessage("Book returned!");
                } else {
                    showMessage("Book not found or not borrowed!");
                }

            } catch (NumberFormatException ex) {
                showMessage("Enter a valid Book ID!");
            }
        });

        // Display all books in the library.
        showButton.setOnAction(e -> {
            updateList(bookList);
        });

        // Arrange the buttons and input fields in the window.
        HBox buttons1 = new HBox(10,
                addButton, removeButton, searchButton);

        HBox buttons2 = new HBox(10,
                borrowButton, returnButton, showButton);

        VBox root = new VBox(10,
                title,
                idField,
                nameField,
                authorField,
                categoryField,
                buttons1,
                buttons2,
                bookList
        );

        root.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(root, 900, 550);

        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    // Converts book information into a readable format for the list.
    private String bookInfo(Book book) {

        String status;

        if (book.isBorrowed()) {
            status = "Borrowed";
        } else {
            status = "Available";
        }

        return book.getId() + " - " +
                book.getTitle() + " - " +
                book.getAuthor() + " - " +
                book.getCategory() + " - " + status;
    }

    // Refreshes the displayed book list after an operation.
    private void updateList(ListView<String> bookList) {

        bookList.getItems().clear();

        for (Book book : bookService.getBooks()) {
            bookList.getItems().add(bookInfo(book));
        }
    }

    // Shows a message about the result of an operation.
    private void showMessage(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Starts the JavaFX application.
    public static void main(String[] args) {
        launch(args);
    }
}
