package bookstoretest.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao {

    @Query("select * from books")
    LiveData<List<Book>> getAllBooks();

    @Query("select * from books where bookTitle=:title")
    List<Book> getBook(String title);

    @Insert
    void addBook(Book book);

    @Query("delete from books where bookTitle= :title")
    void deleteBook(String title);

    @Query("DELETE FROM books WHERE bookId IN (SELECT bookId FROM books ORDER BY bookId DESC LIMIT 1)")
    void deleteLastBook();

    @Query("delete FROM books")
    void deleteAllBooks();
}
