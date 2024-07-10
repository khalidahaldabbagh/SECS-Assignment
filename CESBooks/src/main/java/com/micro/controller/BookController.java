package com.micro.controller;

import com.micro.entity.Book;
import com.micro.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping(value = "/books",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Book>> findBooks(){
        List<Book> books=bookService.getBooks();
        return ResponseEntity.ok().body(books);
    }

    @GetMapping(value = "/books/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Book> findBookById(@PathVariable("id") long id){
        return ResponseEntity.ok().body(bookService.findById(id));
    }

    @PutMapping(value = "/books/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> updateBookById(@PathVariable("id") long id, @RequestBody Book book){

        return ResponseEntity.status(HttpStatus.CREATED).body( bookService.updateBook(id,book));
    }
    @PostMapping(value = "/books",consumes =MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Book> insertBook(@RequestBody Book book){

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.insertBook(book));
    }

    @DeleteMapping(value="/books/{id}")
    ResponseEntity<?> deleteBook(@PathVariable("id")long id){
        return ResponseEntity.ok().build();
    }

}
