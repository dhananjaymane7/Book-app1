package com.books_management.demo.Service;

import com.books_management.demo.Entity.Book;
import java.util.Optional;

public interface BookService {

    // Method to add a new book
    boolean addBook(Book book);

    // Method to update an existing book by its ID
    boolean updateBook(Long id, Book updatedBook);

    // Method to delete a book by its ID
    boolean deleteBook(Long id);

    // Method to get a book by its ID
    Optional<Book> getBookById(Long id);

    // Method to get all books
    Iterable<Book> getAllBooks();

    String getBookImage(String bookTitle);
}
