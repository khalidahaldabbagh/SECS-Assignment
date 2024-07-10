package com.micro.service;

import com.micro.entity.Book;

import java.util.List;

public interface BookService {
    Book insertBook(Book book);
    List<Book> getBooks();
    void deleteBookById(long id);
    Book findById(long id);

    Book updateBook(long id,Book book);

    List<Book> getAllBooks();
}
