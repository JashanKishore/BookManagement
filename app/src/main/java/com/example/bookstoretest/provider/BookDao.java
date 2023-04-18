package com.example.bookstoretest.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookstoretest.Book;

import java.util.List;

public interface BookDao {

    @Query("select * from books")
    LiveData<List<Book>> getAllBooks();

    @Query("select * from books where bookTitle=:title")
    List<Book> getBook(String title);

    @Insert
    void addBook(Book book);

    @Query("delete from books where bookTitle= :title")
    void deleteBook(String title);

    @Query("delete FROM books")
    void deleteAllBooks();
}
