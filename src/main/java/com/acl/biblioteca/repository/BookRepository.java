package com.acl.biblioteca.repository;

import com.acl.biblioteca.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT new map(b.id as id, b.nombre as nombre, b.imagen as imagen, b.precio as precio, b.autor as autor) FROM Book b")
    Page<Object[]> findAllBooks(Pageable pageable);
}
