package com.fardeen.budget_planner;

public class History {
    private float total;
    private float change;
    private String type;
    private String date;

    public History(float total, float change, String type, String date) {
        this.total = total;
        this.change = change;
        this.type = type;
        this.date = date;
    }

    @Override
    public String toString() {
        return  type +
                " " + change +
                "\n" + total +
                "\n" + date;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public float getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
}
