package com.acl.biblioteca.controllers;

import com.acl.biblioteca.Response;
import com.acl.biblioteca.models.Book;
import com.acl.biblioteca.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Book book = bookService.findBook(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<Book> allBooks() {
        return bookService.allBooks();
    }

    @PostMapping("/new")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        Response response = bookService.addBook(book);
        if (response.getStatus() == 201) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(response.getStatus()).body(response);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        Response response = bookService.deleteBook(id);
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(response.getStatus()).body(response);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editBook(@PathVariable("id") Long id, @RequestBody Book updatedBook) {
        Response response = bookService.editBook(id, updatedBook);
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(response.getStatus()).body(response);
        }
    }
}
