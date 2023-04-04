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
    public Week6Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Week6Adapter.ViewHolder holder, int position) {
        holder.idEt.setText(db.get(position).getId());
        holder.isbnEt.setText(db.get(position).getIsbn());
        holder.titleEt.setText(db.get(position).getTitle());
        holder.authorEt.setText(db.get(position).getAuthor());
        holder.priceEt.setText(db.get(position).getPrice());
        holder.descriptionEt.setText(db.get(position).getDescription());
    }

    @Override
    public int getItemCount() {return db.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idEt;
        TextView isbnEt;
        TextView titleEt;
        TextView authorEt;
        TextView priceEt;
        TextView descriptionEt;

        public ViewHolder(@NonNull View bookView) {
            super(bookView);
            idEt = bookView.findViewById(R.id.card_id_id);
            isbnEt = bookView.findViewById(R.id.card_isbn_id);
            titleEt = bookView.findViewById(R.id.card_title_id);
            authorEt = bookView.findViewById(R.id.card_author_id);
            priceEt = bookView.findViewById(R.id.card_price_id);
            descriptionEt = bookView.findViewById(R.id.card_description_id);
        }
    }
}
