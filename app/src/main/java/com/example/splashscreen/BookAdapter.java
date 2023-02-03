package com.example.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nl.siegmann.epublib.domain.Book;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    // creating variables for arraylist and context.
    private ArrayList<BookInfo> bookInfoArrayList;
    private Context mcontext;
    private FavBookDatabaseHandler db;
    private int Userid;

    // creating constructor for array list and context.
    public BookAdapter(ArrayList<BookInfo> bookInfoArrayList, Context mcontext, int Userid) {
        this.bookInfoArrayList = bookInfoArrayList;
        this.mcontext = mcontext;
        this.Userid = new Intent(mcontext, HomeScreen.class).getIntExtra("Userid", Userid);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout for item of recycler view item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_rv_item, parent, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        // inside on bind view holder method we are
        // setting our data to each UI component.
        BookInfo bookInfo = bookInfoArrayList.get(position);
        holder.nameTV.setText(bookInfo.getTitle());
        holder.publisherTV.setText(bookInfo.getPublisher());
        holder.dateTV.setText(bookInfo.getPublishedDate());

        // below line is use to set image from URL in our image view.
        Picasso.get().load(bookInfo.getThumbnail().replace("http:", "https:")).into(holder.bookIV);
        Log.d("test", bookInfo.getThumbnail());

        // below line is use to add on click listener for our item of recycler view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inside on click listener method we are calling a new activity
                // and passing all the data of that item in next intent.
                Intent i = new Intent(mcontext, BookDetailOnline.class);
                i.putExtra("title", bookInfo.getTitle());
                i.putExtra("publisher", bookInfo.getPublisher());
                i.putExtra("publishedDate", bookInfo.getPublishedDate());
                i.putExtra("description", bookInfo.getDescription());
                i.putExtra("thumbnail", bookInfo.getThumbnail());

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

    public class BookViewHolder extends RecyclerView.ViewHolder {
        // below line is use to initialize
        // our text view and image views.
        TextView nameTV, publisherTV, dateTV;
        ImageView bookIV;
        ToggleButton BkmarkTG;

        public BookViewHolder(View itemView) {
            super(itemView);

            db = new FavBookDatabaseHandler(mcontext);
            nameTV = itemView.findViewById(R.id.idRVBookTitle);
            publisherTV = itemView.findViewById(R.id.idRVAuthor);
            dateTV = itemView.findViewById(R.id.idRVDate);
            bookIV = itemView.findViewById(R.id.idRVBookImg);

            // Toggle Button
            BkmarkTG = itemView.findViewById(R.id.btnBookmark);
            BkmarkTG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = nameTV.getText().toString();
                    String publisher = publisherTV.getText().toString();
                    String date = dateTV.getText().toString();
                    String thumbnail = bookIV.toString();
                    Log.d("Userid", String.valueOf(Userid));
                    Log.d("Book", title);
                    if(BkmarkTG.isChecked()) {
                        boolean isAdded = db.addBookmark(Userid, title, publisher, date, thumbnail, "description");
                        if(!isAdded)
                            Toast.makeText(mcontext, "Failed to add Book", Toast.LENGTH_SHORT);
                        Log.d("Added", "Success");
                    } else {
                        boolean isDeleted = db.deleteBookmark(Userid, title, publisher, date, thumbnail, "description");
                        if(!isDeleted)
                            Toast.makeText(mcontext, "Failed to delete Book", Toast.LENGTH_SHORT);
                        Log.d("Delete", "Deleted");
                    };
                }
            });
        }
    }
}
