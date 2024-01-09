package com.acl.biblioteca.services;

import com.acl.biblioteca.models.User;
import com.acl.biblioteca.repository.BookRepository;
import com.acl.biblioteca.response.Response;
import com.acl.biblioteca.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Page<Object[]> allBooks(Pageable pageable) {
        return bookRepository.findAllBooks(pageable);
    }

    public Response addBook(Book book, User user) {
        if (book == null || user == null) {
            return new Response(404, "Not Found", "Problema al crear el libro!", "404 Not Found!");
        }
        book.setUser(user);
        bookRepository.save(book);
        return new Response(201, "Created", "Libro creado con éxito.", "");
    }

    public Book findBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
    }

    public Response editBook(Long id, Book updatedBook) {
        Book existingBook = findBook(id);
        if (existingBook != null) {
            existingBook.setNombre(updatedBook.getNombre());
            existingBook.setAutor(updatedBook.getAutor());
            existingBook.setImagen(updatedBook.getImagen());
            existingBook.setPaginas(updatedBook.getPaginas());
            existingBook.setSinopsis(updatedBook.getSinopsis());

            bookRepository.save(existingBook);
            return new Response(200, "Ok", "Libro actualizado con éxito.", "");
        } else {
            return new Response(404, "Not Found", "El libro no existe!", "404 Not Found!");
        }
    }

    public Response deleteBook(Long id) {
        Book book = findBook(id);
        if (book == null) {
            return new Response(404, "Not Found", "El libro no existe.", "404 Not Found!");
        }
        bookRepository.deleteById(book.getId());
        return new Response(200, "OK", "Libro eliminado con éxito.", "");
    }
}
