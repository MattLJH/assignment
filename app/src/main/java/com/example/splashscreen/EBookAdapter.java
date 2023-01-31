package com.example.splashscreen;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.webkit.WebView;
//
import android.content.Context;
public class EBookAdapter extends RecyclerView.Adapter<EBookAdapter.ebookList> {
    private ArrayList<EBookInfo> ebooks;


    public EBookAdapter(ArrayList<EBookInfo> ebooks) {
        this.ebooks = ebooks;
    }

    public class ebookList extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView author;
        private ImageView thumbnail;
        private Button ebookBtn;

        public ebookList(final View v){
            super(v);
            title = v.findViewById(R.id.ebookTitle);
            author = v.findViewById(R.id.ebookAuthor);
            thumbnail = v.findViewById(R.id.ebookThumbnail);
            ebookBtn = v.findViewById(R.id.ebookBtn);
        }
    }

    @NonNull
    @Override
    public EBookAdapter.ebookList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ebookView = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_ebook, parent, false);
        return new ebookList(ebookView);
    }

    @Override
    public void onBindViewHolder(@NonNull EBookAdapter.ebookList holder, int position) {
        String id = Integer.toString(ebooks.get(position).getId());
        String uri = ebooks.get(position).getUri();
        String title = ebooks.get(position).getTitle();
        String authors = ebooks.get(position).getAuthors();
        Bitmap thumbnail = ebooks.get(position).getThumbnail();

        holder.title.setText(title);
        holder.author.setText(authors);
        holder.thumbnail.setImageBitmap(thumbnail);

        List<String> data = new ArrayList<String>();
        data.add(id);
        data.add(uri);
        holder.ebookBtn.setTag(data);

        //holder.title.setText(uri);
    }

    @Override
    public int getItemCount() {
        return ebooks.size();
    }
}
