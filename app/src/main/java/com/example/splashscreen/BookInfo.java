package com.example.splashscreen;

import java.util.ArrayList;

public class BookInfo {

    private int id;
    private String title;
    private String publisher;
    private String publishedDate;
    private String description;
    private String thumbnail;

    // creating getter and setter methods
    public int getId() {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    // creating a constructor class for our BookInfo
    public BookInfo(String title, String publisher, String publishedDate, String description, String thumbnail) {
        this.title = title;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public BookInfo() {

    }

    public BookInfo(int id, String title) {
        this.id = id;
        this.title = title;
    }
}