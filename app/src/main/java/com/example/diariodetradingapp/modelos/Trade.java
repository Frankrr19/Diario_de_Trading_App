package com.example.diariodetradingapp.modelos;

public class Trade {
    private String state;
    private String entry;
    private int year;
    private int day;
    private String month;
    private String market;
    private Float contracts;
    private Float pointValue;
    private Float points;
    private String emotion;
    private Float total;

    public Trade() {
    }

    public Trade(String state, String entry, int year, int day, String month, String market, Float contracts, Float pointValue, Float points, String emotion) {
        this.state = state;
        this.entry = entry;
        this.year = year;
        this.day = day;
        this.month = month;
        this.market = market;
        this.contracts = contracts;
        this.pointValue = pointValue;
        this.points = points;
        this.emotion = emotion;
        this.total = this.contracts * this.pointValue * this.points;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
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

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public Float getContracts() {
        return contracts;
    }

    public void setContracts(Float contracts) {
        this.contracts = contracts;
    }

    public Float getPointValue() {
        return pointValue;
    }

    public void setPointValue(Float pointValue) {
        this.pointValue = pointValue;
    }

    public Float getPoints() {
        return points;
    }

    public void setPoints(Float points) {
        this.points = points;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
