package com.example.budget_planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton FabPlus;
    private Button historyActivity;
    private Button deleteDb;
    private Button switchChart;
    private TextView totalBalance;
    private EditText inputMoney;
    private static float Balance;
    private boolean state = true;

    Database database;
    private ArrayList <String> incomeList, filteredIncomeList;
    private ArrayList <Float> totalSum, filteredTotalSum;

    public float getBalance(){ return Balance;}
    public void  setBalance(float newBalance){ MainActivity.Balance = newBalance;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Load Prefs
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        Balance = sharedPreferences.getFloat("BALANCE", 0.0f);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Fab activity change
        FabPlus =(FloatingActionButton)findViewById(R.id.fab_button);
        FabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExtension();
            }
        });
        totalBalance = (TextView) findViewById(R.id.totalBalance);
        totalBalance.setText("$"+String.valueOf(Balance));

        //History activity change
        historyActivity = (Button)findViewById(R.id.historyBtn);
        historyActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistory();
            }
        });

        //Delete Database
        deleteDb = (Button)findViewById(R.id.deleteDbBtn);
        deleteDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.deleteDatabase("history.db");
                updateChart();
            }
        });

        switchChart = (Button)findViewById(R.id.switchChart);
        switchChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = !state;
                updateChart();
            }
        });

        //Setup PieChart
        updateChart();

    }

    @Override
    protected void onRestart () {
        super.onRestart();
        totalBalance = (TextView) findViewById(R.id.totalBalance);
        totalBalance.setText("$"+String.valueOf(Balance));
    }

    @Override
    protected void onResume () {
        super.onResume();
        updateChart();
    }

    @Override
    protected void onPause () {
        super.onPause();
        //Prefs
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("BALANCE", Balance);
        editor.apply();
    }

    public void openExtension () {
        Intent intent = new Intent(this, FAB_PLUS_EXTENSION.class);
        startActivity(intent);
    }

    public void openHistory () {
        Intent intent = new Intent(this, TransactionHistory.class);
        startActivity(intent);
    }

    public void setupPieChart () {
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
        PieChart chart = (PieChart)findViewById(R.id.incomeChart);
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

    public void updateChart () {
        database = new Database(MainActivity.this);
        incomeList = new ArrayList<>();
        filteredIncomeList = new ArrayList<>();
        totalSum = new ArrayList<>();
        filteredTotalSum = new ArrayList<>();
        storeData();
        filterData();
        setupPieChart();
    }
}