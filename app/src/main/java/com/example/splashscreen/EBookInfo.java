package com.example.splashscreen;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class EBookInfo {
    private int id;
    private String uri;
    private String title;
    private String authors;
    private Bitmap thumbnail;

    public EBookInfo() {

    }

    public EBookInfo(String uri) {
        this.uri = uri;
    }

    public EBookInfo(int id, String uri) {
        this.id = id;
        this.uri = uri;
    }

    public EBookInfo(int id, String uri, String title, String authors, Bitmap thumbnail) {
        this.id = id;
        this.uri = uri;
        this.title = title;
        this.authors = authors;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
