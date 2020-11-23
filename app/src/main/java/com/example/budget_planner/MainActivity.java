package com.example.budget_planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton FabPlus;
    private Button historyActivity;
    private Button deleteDb;
    private TextView totalBalance;
    private EditText inputMoney;
    private static float Balance;

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
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        totalBalance = (TextView) findViewById(R.id.totalBalance);
        totalBalance.setText("$"+String.valueOf(Balance));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Prefs
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("BALANCE", Balance);
        editor.apply();
    }

    public void openExtension(){
        Intent intent = new Intent(this, FAB_PLUS_EXTENSION.class);
        startActivity(intent);
    }

    public void openHistory(){
        Intent intent = new Intent(this, TransactionHistory.class);
        startActivity(intent);
    }


}