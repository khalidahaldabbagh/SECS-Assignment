package com.micro.service.impl;

import com.micro.entity.Book;
import com.micro.repository.BookRepository;
import com.micro.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book insertBook(Book book) {
     return bookRepository.save(book);

    }

    @Override
    public List<Book> getBooks() {

        return bookRepository.findAll();

    }

    @Override
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book findById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> {
            log.error("Book with id {} not found", id);
            return new EntityNotFoundException("Book not found with id: " + id);
        });
        return book;
    }

    @Override
    public Book updateBook(long id,Book updatedBook) {
        Book book=findById(id);
        updatedBook.setIsbn(book.getIsbn());
        return bookRepository.save(updatedBook);
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookByIsbn(String isbn) {
        return bookRepository.findById(Long.valueOf(isbn)).orElse(null);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }
}
