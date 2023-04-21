package bookstoretest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstoretest.R;
import com.example.bookstoretest.provider.Book;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Book> db;

    public RecyclerAdapter() {

    }

    public void setBooks(List<Book> NewData) {
        db = NewData;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.idEt.setText(String.valueOf(db.get(position).getId()));
        holder.isbnEt.setText(db.get(position).getIsbn());
        holder.titleEt.setText(db.get(position).getTitle());
        holder.authorEt.setText(db.get(position).getAuthor());
        holder.priceEt.setText(String.valueOf(db.get(position).getPrice()));
        holder.descriptionEt.setText(db.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        if (db == null)
            return 0;
        else
            return db.size();
    }

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
