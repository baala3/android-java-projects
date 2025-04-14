package com.example.mynotes.model;

public class Item {
    int id;
    String title;
    String desc;
    String amount;
    String color;
    String date;

    public Item() {

    }

    public Item(String title, String desc, String amount, String color,String date) {
        this.title = title;
        this.desc = desc;
        this.amount = amount;
        this.color = color;
        this.date = date;
    }

    public Item(int id, String title, String desc, String amount, String color,String date) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.amount = amount;
        this.color = color;
        this.date = date;
    }

    public Item(int id, String title, String desc, String amount, String color) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.amount = amount;
        this.color = color;
    }

    public Item(String title, String desc, String amount, String color) {
        this.title = title;
        this.desc = desc;
        this.amount = amount;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getAmount() {
        return amount;
    }

    public String getColor() {
        return color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
