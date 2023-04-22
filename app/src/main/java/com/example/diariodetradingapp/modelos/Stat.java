package com.example.diariodetradingapp.modelos;

public class Stat{
    private Float initialCash;
    private Float total;
    private Float profit;
    private Float stop;

    public Stat() {
    }

    public Stat(Float initialCash, Float profit, Float stop) {
        this.initialCash = initialCash;
        this.profit = profit;
        this.stop = stop;
        this.total = (this.profit + this.stop) + this.initialCash;
    }

    public Float getInitialCash() {
        return initialCash;
    }

    public void setInitialCash(Float initialCash) {
        this.initialCash = initialCash;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getProfit() {
        return profit;
    }

    public void setProfit(Float profit) {
        this.profit = profit;
    }

    public Float getStop() {
        return stop;
    }

    public void setStop(Float stop) {
        this.stop = stop;
    }
}
