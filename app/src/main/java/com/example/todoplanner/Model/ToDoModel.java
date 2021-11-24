package com.example.todoplanner.Model;

public class ToDoModel {
    private int id,status;
    private String text;

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setText(String text) {
        this.text = text;
    }

}
