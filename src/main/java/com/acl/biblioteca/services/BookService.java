package com.acl.biblioteca.services;

import com.acl.biblioteca.Response;
import com.acl.biblioteca.models.Book;
import com.acl.biblioteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    public Response addBook(Book book) {
        if (book == null) {
            //TODO: ver el status del codigo
            return new Response(404, "Not Found", "Problema al crear el libro!");
        }
        bookRepository.save(book);
        return new Response(201, "", "Libro creado con éxito.");
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
            existingBook.setPrecio(updatedBook.getPrecio());
            existingBook.setSinopsis(updatedBook.getSinopsis());

            bookRepository.save(existingBook);
            return new Response(200, "", "Libro actualizado con éxito.");
        } else {
            return new Response(404, "Not Found", "El libro no existe.");
        }
    }

    public Response deleteBook(Long id) {
        Book book = findBook(id);
        if (book == null) {
            return new Response(404, "Not Found", "El libro no existe.");
        }
        bookRepository.deleteById(book.getId());
        return new Response(200, "", "Libro eliminado con éxito.");
    }
}
