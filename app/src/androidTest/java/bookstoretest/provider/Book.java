package bookstoretest.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "bookId")
    private int id;
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

    public Book(String title, String author, String description, String isbn, String price) {

        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.price = price;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public int getId() {
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

}
