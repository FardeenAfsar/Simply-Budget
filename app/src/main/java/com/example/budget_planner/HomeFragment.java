package com.example.budget_planner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FloatingActionButton FabPlus;
    private Button deleteDb;
    private Button switchChart;
    private TextView totalBalance;
    private boolean state = true;

    Database database;
    private ArrayList<String> incomeList, filteredIncomeList;
    private ArrayList <Float> totalSum, filteredTotalSum;
    private float Balance;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        if (getArguments() != null) { Balance = getArguments().getFloat("Balance"); }
        //Fab activity change
        FabPlus =(FloatingActionButton)v.findViewById(R.id.fab_button);
        FabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExtension();
            }
        });
        totalBalance = (TextView)v.findViewById(R.id.totalBalance);
        totalBalance.setText("$"+String.valueOf(Balance));

        //Delete Database
        deleteDb = (Button)v.findViewById(R.id.deleteDbBtn);
        deleteDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().deleteDatabase("history.db");
                updateChart(v);
            }
        });

        switchChart = (Button)v.findViewById(R.id.switchChart);
        switchChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = !state;
                updateChart(v);
            }
        });
        //Setup PieChart
        updateChart(v);

        return v;
    }

    public void openExtension () {
        Intent intent = new Intent(getActivity(), FAB_PLUS_EXTENSION.class);
        startActivity(intent);
    }

    public void setupPieChart (View v) {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < filteredTotalSum.size(); i++) {
            pieEntries.add(new PieEntry(filteredTotalSum.get(i), filteredIncomeList.get(i)));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, null);
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        pieDataSet.setValueTextSize(14f);
        pieDataSet.setUsingSliceColorAsValueLineColor(true);
        pieDataSet.setValueLinePart1OffsetPercentage(30);

        PieData data = new PieData(pieDataSet);
        PieChart chart = (PieChart)v.findViewById(R.id.incomeChart);
        pieDataSet.setValueFormatter(new CustomFormatter(chart));
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setData(data);
        chart.animateXY(1000,1000);
        chart.setMinAngleForSlices(2);
        chart.setEntryLabelColor(Color.parseColor("#000000"));
        chart.setEntryLabelTextSize(14f);
        chart.setDrawEntryLabels(false);

        Legend legend = chart.getLegend();
        legend.setTextSize(15f);

        chart.notifyDataSetChanged();
        chart.invalidate();
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
        if (state) {
            for (int i = 0; i < size; i++) {
                if (totalSum.get(i) < 0) {
                    totalSum.remove(i);
                    incomeList.remove(i);
                    i--;
                    size--;
                }
            }
        }
        else {
            for (int i = 0; i < size; i++) {
                if (totalSum.get(i) > 0) {
                    totalSum.remove(i);
                    incomeList.remove(i);
                    i--;
                    size--;
                }else{
                    totalSum.set(i, -(totalSum.get(i)));
                }
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
    }

    public void updateChart (View v) {
        database = new Database(getActivity());
        incomeList = new ArrayList<>();
        filteredIncomeList = new ArrayList<>();
        totalSum = new ArrayList<>();
        filteredTotalSum = new ArrayList<>();
        storeData();
        filterData();
        setupPieChart(v);
    }


}


