package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavBookAdapter extends RecyclerView.Adapter<FavBookAdapter.BookViewHolder> {

    // creating variables for arraylist and context.
    private ArrayList<BookInfo> bookInfoArrayList;
    private final Context mcontext;
    private FavBookDatabaseHandler db;
    private int Userid;

    // creating constructor for array list and context.
    public FavBookAdapter(ArrayList<BookInfo> bookInfoArrayList, Context mcontext, int Userid) {
        this.bookInfoArrayList = bookInfoArrayList;
        this.mcontext = mcontext;
        this.Userid = Userid;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout for item of recycler view item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_rv_item_bkmarked, parent, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, @SuppressLint("RecyclerView") int position) {

        // inside on bind view holder method we are
        // setting our data to each UI component.;
        holder.nameTV.setText(bookInfoArrayList.get(position).getTitle());
        holder.publisherTV.setText(bookInfoArrayList.get(position).getPublisher());
        holder.dateTV.setText(bookInfoArrayList.get(position).getPublishedDate());

        // below line is use to set image from URL in our image view.
//
        Picasso.get().load(bookInfoArrayList.get(position).getThumbnail().replace("http:", "https:")).into(holder.bookIV);
        Log.d("test", bookInfoArrayList.get(position).getThumbnail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inside on click listener method we are calling a new activity
                // and passing all the data of that item in next intent.
                Intent i = new Intent(mcontext, BookDetailOnline.class);
                i.putExtra("title", bookInfoArrayList.get(position).getTitle());
                i.putExtra("publisher", bookInfoArrayList.get(position).getPublisher());
                i.putExtra("publishedDate", bookInfoArrayList.get(position).getPublishedDate());
                i.putExtra("description", bookInfoArrayList.get(position).getDescription());
                i.putExtra("thumbnail", bookInfoArrayList.get(position).getThumbnail());

                // after passing that data we are
                // starting our new intent.
                mcontext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        // inside get item count method we
        // are returning the size of our array list
        return bookInfoArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        // below line is use to initialize
        // our text view and image views.
        TextView nameTV, publisherTV, dateTV;
        ImageView bookIV;
        Button CrossBtn;

        public BookViewHolder(View itemView) {
            super(itemView);
            db = new FavBookDatabaseHandler(mcontext);
            nameTV = itemView.findViewById(R.id.idRVBookTitle);
            publisherTV = itemView.findViewById(R.id.idRVAuthor);
            dateTV = itemView.findViewById(R.id.idRVDate);
            bookIV = itemView.findViewById(R.id.idRVBookImg);

            CrossBtn = itemView.findViewById(R.id.btnCross);
            CrossBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = nameTV.getText().toString();
                    String publisher = publisherTV.getText().toString();
                    String date = dateTV.getText().toString();
                    String thumbnail = bookIV.toString();
                    Log.d("FavBookAdapter Userid", String.valueOf(Userid));
                    boolean isDeleted = db.deleteBookmark(Userid, title);
                    Log.d("Delete", "isDeleted: " + isDeleted);
                    Log.d("Title", title);
                }
            });
        }
    }
}
