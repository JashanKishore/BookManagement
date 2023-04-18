package com.example.bookstoretest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bookstoretest.provider.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText idEditText, titleEditText, isbnEditText, authorEditText, descriptionEditText, priceEditText, pagesEditText;

    public static final String TITLE_KEY = "bookTitle-key";
    public static final String ISBN_KEY = "isbn-key";
    public static final String SHARED_PREFS = "shared-prefs";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String ISBN = "isbn";
    public static final String AUTHOR = "author";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";

    private String id;
    private String title;
    private String isbn;
    private String author;
    private String description;
    private String price;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;


    ArrayList<Book> books = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Week6Adapter adapter;

    private BookViewModel mBookViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawyer_layout_main);

        //Create reference to UI element i.e., Instantiate the UI elements in code
        idEditText = findViewById(R.id.idEditText);
        titleEditText = findViewById(R.id.titleEditText);
        isbnEditText = findViewById(R.id.isbnEditText);
        authorEditText = findViewById(R.id.authorEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Set the adapter
        adapter = new Week6Adapter();
        adapter.setData(books);
        recyclerView.setAdapter(adapter);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_menu_open, R.string.nav_menu_close);
        drawerLayout.addDrawerListener(toggle);

        /*
        Called to ensure that the toggle's visual appearance and behavior are in sync with the current
        state of the navigation drawer. This is particularly important when the activity's state is
        restored, for example, after a configuration change (like screen rotation), as it ensures
        that the toggle displays the correct icon and state.
         */
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.removeLastBookOption) {
                    removeLastBookFromRecyclerView();
                    Toast.makeText(MainActivity.this, "Last book removed",
                            Toast.LENGTH_SHORT).show();
                } else if (id == R.id.clearAllBooksOption) {
                    clearBooksFromRecyclerView();
                    Toast.makeText(MainActivity.this, "All books cleared",
                            Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addBookTitleToList();
                addBookToRecyclerView();
                saveBookData();
                Toast.makeText(MainActivity.this, "Book data saved.", Toast.LENGTH_SHORT).show();
            }
        });

        //Automatically load attributes from last book
        if (savedInstanceState == null || savedInstanceState.isEmpty()) {
            loadBookData();
            updateViews();
        }

        ///Get SMS permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS, android.Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        //instantiate broadcast receiver
        //This class listens to messages from SMSReceiver
        broadcastReceiver broadcastReceiver = new broadcastReceiver();
        //Register handler with intent filter declared in SMS receiver class
        registerReceiver(broadcastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

    }

    public void addBookToRecyclerView() {
        String id_b = idEditText.getText().toString();
        String title_b = titleEditText.getText().toString();
        String isbn_b = isbnEditText.getText().toString();
        String author_b = authorEditText.getText().toString();
        String description_b = descriptionEditText.getText().toString();
        String price_b = priceEditText.getText().toString();
        books.add(new Book(id_b, title_b, isbn_b, author_b, description_b, price_b));
        adapter.notifyDataSetChanged();
    }

    public void removeLastBookFromRecyclerView() {
        books.remove(books.size() - 1);
        adapter.notifyDataSetChanged();
    }

    public void clearBooksFromRecyclerView() {
        books.clear();
        adapter.notifyDataSetChanged();
    }


    public void clearFields() {
        idEditText.setText("");
        isbnEditText.setText("");
        titleEditText.setText("");
        descriptionEditText.setText("");
        priceEditText.setText("");
        authorEditText.setText("");
    }


    //LAB 3 Task 1: Save and restore book title and ISBN when changing orientation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Saving the non-view data manually to bundle
        outState.putString(TITLE_KEY, titleEditText.getText().toString());
        outState.putString(ISBN_KEY, isbnEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        // Restore the data from bundle
        titleEditText.setText(inState.getString(TITLE_KEY));
        isbnEditText.setText(inState.getString(ISBN_KEY));
    }

    //LAB 3 Task 2: App must load previous book data
    public void saveBookData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Save the Key-Value pair to bundle
        editor.putString(TITLE, titleEditText.getText().toString());
        editor.putString(ID, idEditText.getText().toString());
        editor.putString(AUTHOR, authorEditText.getText().toString());
        editor.putString(DESCRIPTION, descriptionEditText.getText().toString());
        editor.putString(ISBN, isbnEditText.getText().toString());
        editor.putString(PRICE, priceEditText.getText().toString());

        editor.apply();
    }


    public void loadBookData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        title = sharedPreferences.getString(TITLE, "");
        id = sharedPreferences.getString(ID, "");
        isbn = sharedPreferences.getString(ISBN, "");
        author = sharedPreferences.getString(AUTHOR, "");
        description = sharedPreferences.getString(DESCRIPTION, "");
        price = sharedPreferences.getString(PRICE, "");
        updateViews();

    }


    public void updateViews() {
        titleEditText.setText(title);
        idEditText.setText(id);
        authorEditText.setText(author);
        descriptionEditText.setText(description);
        isbnEditText.setText(isbn);
        priceEditText.setText(price);
    }

    class broadcastReceiver extends BroadcastReceiver {
        //onReceive executed every time SMSReceiver sends broadcast
        @Override
        public void onReceive(Context context, Intent intent) {
            //Get message from intent
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);

            //Tokenizer used to parse sms message
            StringTokenizer sT = new StringTokenizer(msg, "|");
            String msgId = sT.nextToken();
            String msgTitle = sT.nextToken();
            String msgIsbn = sT.nextToken();
            String msgAuthor = sT.nextToken();
            String msgDescription = sT.nextToken();
            String msgPrice = sT.nextToken();

            //Update UI with parsed message 
            titleEditText.setText(msgTitle);
            idEditText.setText(msgId);
            authorEditText.setText(msgAuthor);
            descriptionEditText.setText(msgDescription);
            isbnEditText.setText(msgIsbn);
            priceEditText.setText(msgPrice);
            //pagesEditText.setText(msgPages);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clearItemsOption) {
            Toast.makeText(MainActivity.this, "Items cleared",
                    Toast.LENGTH_SHORT).show();
            clearFields();
        } else if (id == R.id.loadItemsOption) {
            Toast.makeText(MainActivity.this, "Items loaded",
                    Toast.LENGTH_SHORT).show();
            loadBookData();
        }
        return true;
    }
}
