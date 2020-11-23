package com.example.budget_planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionHistory extends AppCompatActivity {
    Database database;
    private ArrayList <String> historyType, historyTotal, historyChange, historyDate;
    CustomAdapter customAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        recyclerView = findViewById(R.id.recyclerview);

        database = new Database(TransactionHistory.this);
        historyType = new ArrayList<>();
        historyTotal = new ArrayList<>();
        historyChange = new ArrayList<>();
        historyDate = new ArrayList<>();

        //recycler view
        displayData();
        customAdapter = new CustomAdapter(TransactionHistory.this, historyType, historyTotal, historyChange, historyDate);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TransactionHistory.this));
    }

    public void  displayData () {
        Cursor cursor = database.readData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this,"No History", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                historyTotal.add(cursor.getString(1));
                historyChange.add(cursor.getString(2));
                historyType.add(cursor.getString(3));
                historyDate.add(cursor.getString(4));
            }
        }
        Collections.reverse(historyType);
        Collections.reverse(historyTotal);
        Collections.reverse(historyChange);
        Collections.reverse(historyDate);

    }
}