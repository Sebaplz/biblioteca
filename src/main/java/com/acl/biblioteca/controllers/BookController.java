package com.acl.biblioteca.controllers;

import com.acl.biblioteca.models.User;
import com.acl.biblioteca.response.Response;
import com.acl.biblioteca.models.Book;
import com.acl.biblioteca.services.BookService;
import com.acl.biblioteca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Book book = bookService.findBook(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public Page<Object[]> getAllBooks(Pageable pageable) {
        return bookService.allBooks(pageable);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createBook(@RequestBody Book book, @RequestParam String email) {
        User user = userService.findUser(email);
        if (userService.isAdmin(user)) {
            Response response = bookService.addBook(book, user);
            if (response.getStatus() == 201) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(response.getStatus()).body(response);
            }
        } else {
            return ResponseEntity.status(403).body(new Response(403, "Forbidden", "No tienes permisos para realizar esta acción", "403 Forbidden"));
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id, @RequestParam String email) {
        User user = userService.findUser(email);
        if (userService.isAdmin(user)) {
            Response response = bookService.deleteBook(id);
            if (response.getStatus() == 200) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(response.getStatus()).body(response);
            }
        } else {
            return ResponseEntity.status(403).body(new Response(403, "Forbidden", "No tienes permisos para realizar esta acción", "403 Forbidden"));
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateBook(@PathVariable("id") Long id, @RequestBody Book updatedBook,  @RequestParam String email) {
        User user = userService.findUser(email);
        if (userService.isAdmin(user)) {
            Response response = bookService.editBook(id, updatedBook);
            if (response.getStatus() == 200) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(response.getStatus()).body(response);
            }
        } else {
            return ResponseEntity.status(403).body(new Response(403, "Forbidden", "No tienes permisos para realizar esta acción", "403 Forbidden"));
        }
    }

}
