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
    private String stopLoss;
    private String takeProfit;
    private Boolean takeProfitOrLoss;

    public Trade() {
    }

    public Trade(String state, String entry, int year, int day, String month, String market, Float contracts, Float pointValue, Float points, String emotion, String stopLoss, String takeProfit, Boolean takeProfitOrLoss) {
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
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
        this.takeProfitOrLoss = takeProfitOrLoss;
        this.total = calculateTotal(this.contracts, this.pointValue, this.points, this.takeProfitOrLoss);
    }

    public Trade(Trade trade) {
        state = trade.getState();
        entry = trade.getEntry();
        year = trade.getYear();
        day = trade.getDay();
        month = trade.getMonth();
        market = trade.getMarket();
        contracts = trade.getContracts();
        pointValue = trade.getPointValue();
        points = trade.getPoints();
        emotion = trade.getEmotion();
        stopLoss = trade.getStopLoss();
        takeProfit = trade.getTakeProfit();
        takeProfitOrLoss = trade.getTakeProfitOrLoss();
        total = trade.getTotal();
    }

    private Float calculateTotal(Float contracts, Float pointValue, Float points, Boolean takeProfitOrLoss) {
        float total = 0;
        if (takeProfitOrLoss){
            total = contracts * pointValue * points;
        }else{
            total = (contracts * pointValue * points) * (-1);
        }
        return total;
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

    public String getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(String stopLoss) {
        this.stopLoss = stopLoss;
    }

    public String getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(String takeProfit) {
        this.takeProfit = takeProfit;
    }

    public Boolean getTakeProfitOrLoss() {
        return takeProfitOrLoss;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "state='" + state + '\'' +
                ", entry='" + entry + '\'' +
                ", year=" + year +
                ", day=" + day +
                ", month='" + month + '\'' +
                ", market='" + market + '\'' +
                ", contracts=" + contracts +
                ", pointValue=" + pointValue +
                ", points=" + points +
                ", emotion='" + emotion + '\'' +
                ", total=" + total +
                ", stopLoss='" + stopLoss + '\'' +
                ", takeProfit='" + takeProfit + '\'' +
                ", takeProfitOrLoss=" + takeProfitOrLoss +
                '}';
    }

    public void setTakeProfitOrLoss(Boolean takeProfitOrLoss) {
        this.takeProfitOrLoss = takeProfitOrLoss;
    }
}
