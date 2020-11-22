package com.example.budget_planner;

public class History {
    private float total;
    private float change;
    private String type;

    public History(float total, float change, String type) {
        this.total = total;
        this.change = change;
        this.type = type;
    }

    @Override
    public String toString() {
        return  type +
                "   " + change +
                "\n          " + total;
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
}
