package com.example.budget_planner;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class CustomFormatter extends ValueFormatter
{

    public DecimalFormat mFormat;
    private PieChart pieChart;

    public CustomFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    // Can be used to remove percent signs if the chart isn't in percent mode
    public CustomFormatter(PieChart pieChart) {
        this();
        this.pieChart = pieChart;
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + " %";
    }


    @Override
    public String getPieLabel(float value, PieEntry pieEntry) {
        if (value < 5) {
            return "";
        }
        if (pieChart != null && pieChart.isUsePercentValuesEnabled()) {
            // Converted to percent
            return getFormattedValue(value);
        } else {
            // raw value, skip percent sign
            return mFormat.format(value);
        }
    }

}
