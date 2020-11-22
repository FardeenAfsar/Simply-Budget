package com.example.budget_planner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

public class TransactionHistory extends AppCompatActivity {
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        list = findViewById(R.id.historyList);
        showDatabase(list);
    }

    public void showDatabase (ListView list) {
        Database database = new Database(TransactionHistory.this);
        List<History> all = database.getAll();
        Collections.reverse(all);
        ArrayAdapter historyArrayAdapter = new ArrayAdapter<History>(TransactionHistory.this, android.R.layout.simple_list_item_1, all);
        list.setAdapter(historyArrayAdapter);
    }
}