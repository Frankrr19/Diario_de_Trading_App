package com.example.diariodetradingapp.modelos;

public class Review {
    private int year;
    private int day;
    private String month;
    private String text;

    public Review() {
    }

    public Review(int year, int day, String month, String text) {
        this.year = year;
        this.day = day;
        this.month = month;
        this.text = text;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
