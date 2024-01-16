package com.acl.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String image;
    private int pages;
    private String synopsis;
    private int downloads;

    public BookDto(Long id, String title, String author, String image, int pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.image = image;
        this.pages = pages;
    }

    public BookDto(Long id, String title, String author, int downloads) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.downloads = downloads;
    }
}