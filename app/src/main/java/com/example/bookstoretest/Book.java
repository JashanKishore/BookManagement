package com.example.bookstoretest;

public class Book {
    private String id;
    private String title;
    private String author;
    private String description;
    private String isbn;
    private String price;

    public Book(String id , String title, String author, String description, String isbn, String price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPrice() {
        return price;
    }
}
