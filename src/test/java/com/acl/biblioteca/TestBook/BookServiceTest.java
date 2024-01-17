package com.acl.biblioteca.TestBook;

import com.acl.biblioteca.dto.BookDto;
import com.acl.biblioteca.models.Book;
import com.acl.biblioteca.models.User;
import com.acl.biblioteca.response.Response;
import com.acl.biblioteca.services.BookService;
import com.acl.biblioteca.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Test
    void testAddBook() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test Book");
        bookDto.setAuthor("Test Author");
        bookDto.setImage("test-image.jpg");
        bookDto.setPages(100);

        User user = userService.findUser("sebastian.h.neira@outlook.com");

        Response response = bookService.addBook(bookDto, user);

        assertEquals(201, response.getStatus());
        assertEquals("Created", response.getStatusText());
        assertEquals("Libro creado con éxito.", response.getMessage());
    }

    @Test
    void testFindBookWithExistingBook() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Libro de prueba");
        bookDto.setAuthor("Autor de prueba");
        bookDto.setImage("imagen-prueba.jpg");
        bookDto.setPages(150);

        User user = userService.findUser("sebastian.h.neira@outlook.com");

        Response response = bookService.addBook(bookDto, user);
        assertEquals(201, response.getStatus());

        Long bookId = Long.parseLong(response.getError());

        Book foundBook = bookService.findBook(bookId);

        assertNotNull(foundBook);
        assertEquals("Libro de prueba", foundBook.getTitle());

    }

    @Test
    void testEditBook() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Libro a editar");
        bookDto.setAuthor("Autor de prueba");
        bookDto.setImage("imagen-prueba.jpg");
        bookDto.setPages(150);

        User user = userService.findUser("sebastian.h.neira@outlook.com");

        Response addResponse = bookService.addBook(bookDto, user);
        assertEquals(201, addResponse.getStatus());

        Long bookId = Long.parseLong(addResponse.getError());

        BookDto updatedBookDto = new BookDto();
        updatedBookDto.setTitle("Libro editado");
        updatedBookDto.setAuthor("Nuevo autor");
        updatedBookDto.setImage("nueva-imagen.jpg");
        updatedBookDto.setPages(200);

        Response editResponse = bookService.editBook(bookId, updatedBookDto);

        assertEquals(200, editResponse.getStatus());
        assertEquals("Ok", editResponse.getStatusText());
        assertEquals("Libro actualizado con éxito.", editResponse.getMessage());

        Book editedBook = bookService.findBook(bookId);

        assertNotNull(editedBook);
        assertEquals("Libro editado", editedBook.getTitle());
        assertEquals("Nuevo autor", editedBook.getAuthor());
        assertEquals("nueva-imagen.jpg", editedBook.getImage());
        assertEquals(200, editedBook.getPages());
    }

    @Test
    void testDeleteBook() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Libro a eliminar");
        bookDto.setAuthor("Autor de prueba");
        bookDto.setImage("imagen-prueba.jpg");
        bookDto.setPages(150);

        User user = userService.findUser("sebastian.h.neira@outlook.com");

        Response addResponse = bookService.addBook(bookDto, user);
        assertEquals(201, addResponse.getStatus());

        Long bookId = Long.parseLong(addResponse.getError());

        Response deleteResponse = bookService.deleteBook(bookId);

        assertEquals(200, deleteResponse.getStatus());
        assertEquals("OK", deleteResponse.getStatusText());
        assertEquals("Libro eliminado con éxito.", deleteResponse.getMessage());

        Book deletedBook = bookService.findBook(bookId);

        assertNull(deletedBook);
    }
}
