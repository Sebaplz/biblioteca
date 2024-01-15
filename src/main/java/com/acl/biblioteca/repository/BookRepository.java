package com.acl.biblioteca.repository;

import com.acl.biblioteca.models.Book;
import com.acl.biblioteca.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT new com.acl.biblioteca.dto.BookDto(b.id, b.title, b.author, b.image, b.pages) FROM Book b")
    Page<BookDto> findAllBooksDto(Pageable pageable);
}
