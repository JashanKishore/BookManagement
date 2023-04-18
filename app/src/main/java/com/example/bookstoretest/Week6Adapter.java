package com.example.bookstoretest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Week6Adapter extends RecyclerView.Adapter<Week6Adapter.ViewHolder> {
    ArrayList<Book> db = new ArrayList<Book>();

    public void setData(ArrayList<Book> db) {
        this.db = db;
    }

    @NonNull
    @Override
    // This method is called when the RecyclerView needs a new ViewHolder of the given type to represent an item.
    public Week6Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_layout, parent, false);
        // Create a ViewHolder for the view
        ViewHolder viewHolder = new ViewHolder(view);
        // Return the ViewHolder
        return viewHolder;
    }

    @Override
    // This method is called by the RecyclerView to display the data at the specified position.
    public void onBindViewHolder(@NonNull Week6Adapter.ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the contents of the view with that element
        holder.idEt.setText(db.get(position).getId());
        holder.isbnEt.setText(db.get(position).getIsbn());
        holder.titleEt.setText(db.get(position).getTitle());
        holder.authorEt.setText(db.get(position).getAuthor());
        holder.priceEt.setText(db.get(position).getPrice());
        holder.descriptionEt.setText(db.get(position).getDescription());
        holder.lowercaseTitleEt.setText(db.get(position).getTitle().toLowerCase());
    }

    // This method returns the size of the data set.
    @Override
    public int getItemCount() {return db.size();}

    //A ViewHolder object stores each of the component views inside the tag field of the Layout, so
    // you can immediately access them without the need to look them up repeatedly.

    //The ViewHolder class is a static inner class that holds references to the views in the layout.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idEt;
        TextView isbnEt;
        TextView titleEt;
        TextView authorEt;
        TextView priceEt;
        TextView descriptionEt;
        TextView lowercaseTitleEt;

        // This method is called when the ViewHolder is created.
        //The ViewHolder constructor takes the inflated view and uses findViewById() to get a reference to each subview.
        public ViewHolder(@NonNull View bookView) {
            super(bookView);
            idEt = bookView.findViewById(R.id.card_id_id);
            isbnEt = bookView.findViewById(R.id.card_isbn_id);
            titleEt = bookView.findViewById(R.id.card_title_id);
            authorEt = bookView.findViewById(R.id.card_author_id);
            priceEt = bookView.findViewById(R.id.card_price_id);
            descriptionEt = bookView.findViewById(R.id.card_description_id);
            lowercaseTitleEt = bookView.findViewById(R.id.card_lowercase_title_id);
        }
    }
}
