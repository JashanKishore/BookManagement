package com.example.bookstoretest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    //Declare UI elements
    EditText idEditText, titleEditText, isbnEditText, authorEditText, descriptionEditText, priceEditText;
    Button showToastButton;
    Button clearFieldsButton;
    Button loadInfoButton;

    //LAB 3 T1
    //Why use constants? Because they are used to store key names for SharedPreferences.
    //They are also used to save and restore the book data.
    //Constants are good for re-usability, readability and maintainability.
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create reference to UI element i.e., Instantiate the UI elements in code
        idEditText = findViewById(R.id.idEditText);
        titleEditText = findViewById(R.id.titleEditText);
        isbnEditText = findViewById(R.id.isbnEditText);
        authorEditText = findViewById(R.id.authorEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);
        showToastButton = findViewById(R.id.showToastButton);
        clearFieldsButton = findViewById(R.id.clearFieldsButton);
        loadInfoButton = findViewById(R.id.loadInfoButton);

        //Automatically load attributes from last book
        if(savedInstanceState == null || savedInstanceState.isEmpty()){
            loadBookData();
            updateViews();
        }

        loadInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBookData();
                updateViews();
            }
        });

        /* Request permissions to access SMS */
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS, android.Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        /* Create and instantiate the local broadcast receiver
           This class listens to messages come from class SMSReceiver
         */
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();

        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver @line 11
         * */
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));
    }


    //Previously showToastButton is now used to also save book info for LAB 3
    public void showToast(View view) {
        //Convert to string because that is all we need. Otherwise type will be editable
        String titleInfo = titleEditText.getText().toString();
        //Pass to double since not working with string
        double priceInfo = Double.parseDouble( priceEditText.getText().toString() );

        String message = "Title: " + titleInfo + " | Price: " + priceInfo;
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

        // LAB 3
        saveBookData();
    }

    public void clearFields(View view) {
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
    public void saveBookData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Save the Key-Value pair to bundle
        editor.putString(TITLE, titleEditText.getText().toString());
        editor.putString(ID, idEditText.getText().toString());
        editor.putString(AUTHOR, authorEditText.getText().toString());
        editor.putString(DESCRIPTION, titleEditText.getText().toString());
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
    }


    public void updateViews() {
        titleEditText.setText(title);
        idEditText.setText(id);
        authorEditText.setText(author);
        descriptionEditText.setText(description);
        isbnEditText.setText(isbn);
        priceEditText.setText(price);
    }

    class MyBroadCastReceiver extends BroadcastReceiver {

        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override
        public void onReceive(Context context, Intent intent) {
            /*
             * Retrieve the message from the intent
             * */
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            /*
             * String Tokenizer is used to parse the incoming message
             * The protocol is to have the account holder name and account number separate by a semicolon
             * */

            StringTokenizer sT = new StringTokenizer(msg, ";");
            String id = sT.nextToken();
            String title = sT.nextToken();
            String isbn = sT.nextToken();
            String author = sT.nextToken();
            String description = sT.nextToken();
            String price = sT.nextToken();
            /*
             * Now, its time to update the UI
             * */
            titleEditText.setText(title);
            idEditText.setText(id);
            authorEditText.setText(author);
            descriptionEditText.setText(description);
            isbnEditText.setText(isbn);
            priceEditText.setText(price);


        }
    }
}
