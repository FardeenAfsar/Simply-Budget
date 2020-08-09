package com.example.budget_planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton FabPlus;
    private TextView totalBalance;
    private EditText inputMoney;
    public static float Balance = 2.00f;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String BALANCE = "balance";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get reference to main layout
        //Fab activity change
        FabPlus =(FloatingActionButton)findViewById(R.id.fab_button);
        FabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExtension();
            }
        });
        //Edit Balance
        totalBalance = (TextView) findViewById(R.id.totalBalance);
        totalBalance.setText("$"+String.valueOf(Balance));

        //Save Prefs
    //SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
  //      SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putFloat(BALANCE, Balance);

        //Load Prefs
     //   SharedPreferences sharedPreferences1 = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LayoutInflater inflater = getLayoutInflater();
        View ExtensionLayout = inflater.inflate(R.layout.activity_f_a_b__p_l_u_s__e_x_t_e_n_s_i_o_n, null, false);
        inputMoney =(EditText)ExtensionLayout.findViewById(R.id.inputMoney);
        Balance += Float.parseFloat(inputMoney.getText().toString());
    }

    public void openExtension(){
        Intent intent = new Intent(this, FAB_PLUS_EXTENSION.class);
        startActivity(intent);
    }

}