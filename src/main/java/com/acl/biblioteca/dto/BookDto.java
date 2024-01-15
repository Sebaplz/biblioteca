package com.acl.biblioteca.dto;

import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String image;
    private int pages;
    private String synopsis;

    public BookDto(Long id, String title, String author, String image, int pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.image = image;
        this.pages = pages;
    }
}