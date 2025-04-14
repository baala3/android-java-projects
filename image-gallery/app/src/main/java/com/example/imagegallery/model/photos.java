package com.example.imagegallery.model;

import com.example.imagegallery.model.photo;

import java.util.ArrayList;

public class photos {
    int page;
    int pages;
    int perpage;
    int total;
    ArrayList<photo> photo;

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<com.example.imagegallery.model.photo> getPhoto() {
        return photo;
    }
}
