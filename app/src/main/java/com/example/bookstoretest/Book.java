package com.example.bookstoretest;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "bookId")
    private String id;
    @ColumnInfo(name = "bookTitle")
    private String title;
    @ColumnInfo(name = "bookAuthor")
    private String author;
    @ColumnInfo(name = "bookDescription")
    private String description;
    @ColumnInfo(name = "bookIsbn")
    private String isbn;
    @ColumnInfo(name = "bookPrice")
    private String price;

    public Book(String id , String title, String author, String description, String isbn, String price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.price = price;
    }

    public void setId(@NonNull String id) {
        this.id = id;
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
