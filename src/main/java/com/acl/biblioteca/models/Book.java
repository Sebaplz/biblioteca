package com.acl.biblioteca.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String autor;
    private String imagen;
    private int precio;
    @Lob
    private String sinopsis;
    @Column(updatable = false)
    private Date createdAt;
    private Date updatedAt;
}
