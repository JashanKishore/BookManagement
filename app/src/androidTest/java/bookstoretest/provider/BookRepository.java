package bookstoretest.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

/*
    *
    * BookRepository is a class that abstracts access to multiple data sources.
    * The BookRepository is not part of the Architecture Components libraries, but is a suggested best practice for code separation and architecture.
    * A Repository class handles data operations. It provides a clean API so that the rest of the app can retrieve this data easily.
    * It knows where to get the data from and what API calls to make when data is updated.
    * You can consider repositories to be mediators between different data sources, such as persistent models, web services, and caches.
    *
 */

public class BookRepository {
    private BookDao mBookDao;
    private LiveData<List<Book>> mAllBooks;

    BookRepository(Application application) {
        BookDatabase db = BookDatabase.getDatabase(application);
        mBookDao = db.bookDao();
        mAllBooks = mBookDao.getAllBooks();
    }

    LiveData<List<Book>> getAllBooks() {
        return mAllBooks;
    }

    public void insert(Book book) {
        BookDatabase.databaseWriteExecutor.execute(() -> mBookDao.addBook(book));
    }

    public void deleteLastBook() {
        BookDatabase.databaseWriteExecutor.execute(() -> mBookDao.deleteLastBook());
    }

    public void deleteAll() {
        BookDatabase.databaseWriteExecutor.execute(() -> {
            mBookDao.deleteAllBooks();
        });
    }

}
