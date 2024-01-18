package com.acl.biblioteca.services;

import com.acl.biblioteca.dto.BookDto;
import com.acl.biblioteca.models.User;
import com.acl.biblioteca.repository.BookRepository;
import com.acl.biblioteca.response.Response;
import com.acl.biblioteca.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Page<BookDto> allBooksDto(Pageable pageable) {
        return bookRepository.findAllBooksDto(pageable);
    }

    public Response addBook(BookDto bookDto, User user) {
        if (bookDto == null || user == null) {
            return new Response(404, "Not Found", "Problema al crear el libro!", null,"404 Not Found!");
        }
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setImage(bookDto.getImage());
        book.setPages(bookDto.getPages());
        book.setSynopsis(bookDto.getSynopsis());

        book.setUser(user);
        bookRepository.save(book);
        return new Response(201, "Created", "Libro creado con éxito.", book.getId(), null);
    }

    public Book findBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
    }

    public Response editBook(Long id, BookDto updatedBookDto) {
        Book existingBook = findBook(id);
        if (existingBook != null) {
            existingBook.setTitle(updatedBookDto.getTitle());
            existingBook.setAuthor(updatedBookDto.getAuthor());
            existingBook.setImage(updatedBookDto.getImage());
            existingBook.setPages(updatedBookDto.getPages());
            existingBook.setSynopsis(updatedBookDto.getSynopsis());

            bookRepository.save(existingBook);
            return new Response(200, "Ok", "Libro actualizado con éxito.", existingBook.getId(), null );
        } else {
            return new Response(404, "Not Found", "El libro no existe!", null,"404 Not Found!");
        }
    }

    public Response deleteBook(Long id) {
        Book book = findBook(id);
        if (book == null) {
            return new Response(404, "Not Found", "El libro no existe.", null,"404 Not Found!");
        }
        bookRepository.deleteById(book.getId());
        return new Response(200, "OK", "Libro eliminado con éxito.", book.getId(), null);
    }

    public String increaseDownloadCount(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setDownloads(book.getDownloads() + 1);
            bookRepository.save(book);
            return "Contador de descargas aumentado con éxito";
        } else {
            return "Libro no encontrado";
        }
    }

    public List<BookDto> getAllBooksDownloads(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);
        return bookPage.getContent().stream()
                .map(book -> new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getDownloads()))
                .collect(Collectors.toList());
    }
}
