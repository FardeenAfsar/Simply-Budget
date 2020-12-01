package com.example.budget_planner;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatsFragment extends Fragment {
    Database database;
    private ArrayList <String> incomeList, incomeList2, filteredIncomeList, filteredIncomeList2;
    private ArrayList <Float> totalSum, totalSum2, filteredTotalSum, filteredTotalSum2;
    int[] colors = {Color.parseColor("#C6E3CB"),Color.parseColor("#83CACF"),
            Color.parseColor("#47AED0"),Color.parseColor("#3984B6"),
            Color.parseColor("#2C5A9C"),Color.parseColor("#1E3082"),
            Color.parseColor("#141C59")};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stats, container, false);
        updateChart(v);
        return v;
    }

    public void storeData () {
        Cursor cursor = database.readData();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()){
                incomeList.add(cursor.getString(3));
                totalSum.add(Float.parseFloat(cursor.getString(2)));
            }
        }
        cursor.close();
    }

    public void filterData () {
        int size = incomeList.size();
        for (int i = 0; i < size; i++) {
            if (totalSum.get(i) < 0) {
                totalSum2.add(-totalSum.remove(i));
                incomeList2.add(incomeList.remove(i));
                i--;
                size--;
            }
        }
        for (int i = 0; i < incomeList.size(); i++) {
            if (filteredIncomeList.contains(incomeList.get(i))) {
                int index = filteredIncomeList.indexOf(incomeList.get(i));
                filteredTotalSum.set(index,filteredTotalSum.get(index) + totalSum.get(i));
            }else{
                filteredIncomeList.add(incomeList.get(i));
                filteredTotalSum.add(totalSum.get(i));
            }
        }
        for (int i = 0; i < incomeList2.size(); i++) {
            if (filteredIncomeList2.contains(incomeList2.get(i))) {
                int index = filteredIncomeList2.indexOf(incomeList2.get(i));
                filteredTotalSum2.set(index,filteredTotalSum2.get(index) + totalSum2.get(i));
            }else{
                filteredIncomeList2.add(incomeList2.get(i));
                filteredTotalSum2.add(totalSum2.get(i));
            }
        }
    }

    public void setupBarChart (View v) {
        BarChart barChart = v.findViewById(R.id.barChart);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < filteredTotalSum.size(); i++) {
            barEntries.add(new BarEntry(i,filteredTotalSum.get(i)));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries,null);
        barDataSet.setColors(colors);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(14f);
        BarData barData = new BarData(barDataSet);

        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setValueFormatter(new CustomFormatterCurrency());
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(filteredIncomeList));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.animateY(1000);
        barChart.getDescription().setEnabled(false);

        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }
    public void setupBarChart2 (View v) {
        BarChart barChart = v.findViewById(R.id.barChartExpense);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < filteredTotalSum2.size(); i++) {
            barEntries.add(new BarEntry(i,filteredTotalSum2.get(i)));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries,null);
        barDataSet.setColors(colors);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(14f);
        BarData barData = new BarData(barDataSet);

        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setValueFormatter(new CustomFormatterCurrency());
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(filteredIncomeList2));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.animateY(1000);
        barChart.getDescription().setEnabled(false);

        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

    public void updateChart (View v) {
        database = new Database(getActivity());
        incomeList = new ArrayList<>();
        incomeList2 = new ArrayList<>();
        filteredIncomeList = new ArrayList<>();
        filteredIncomeList2 = new ArrayList<>();
        totalSum = new ArrayList<>();
        totalSum2 = new ArrayList<>();
        filteredTotalSum = new ArrayList<>();
        filteredTotalSum2 = new ArrayList<>();
        storeData();
        filterData();
        setupBarChart(v);
        setupBarChart2(v);
    }

}
