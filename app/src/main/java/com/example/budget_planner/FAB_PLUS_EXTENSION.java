package com.example.budget_planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class FAB_PLUS_EXTENSION extends AppCompatActivity {
    private boolean state = true;
    private String[] incomeOptions = {"income_opt1", "income_opt2", "income_opt3", "income_opt4", "income_opt5"};
    private String[] expenseOptions = {"expense_opt1", "expense_opt2", "expense_opt3", "expense_opt4", "expense_opt5", "expense_opt6", "expense_opt7"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_b__p_l_u_s__e_x_t_e_n_s_i_o_n);

    }
    public void onFabClick (View view){
        EditText inputMoney =(EditText) findViewById(R.id.inputMoney);
        if(inputMoney.getText().toString().isEmpty()){ finish(); }
        else{
            MainActivity mainObj = new MainActivity();
            float tempBalance = Float.parseFloat(inputMoney.getText().toString());
            mainObj.setBalance((state)?mainObj.getBalance() + tempBalance:mainObj.getBalance() - tempBalance);
            finish();
        }

    }
    public void incomeFab (View view){
        ExtendedFloatingActionButton incFab = (ExtendedFloatingActionButton) findViewById(R.id.incomeFab);
        ExtendedFloatingActionButton expFab = (ExtendedFloatingActionButton) findViewById(R.id.expenseFab);
        incFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#03DAC5")));
        expFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
        state = true;
        enableButtons(incomeOptions);
        disableButtons(expenseOptions);
    }
    public void expenseFab (View view){
        ExtendedFloatingActionButton expFab = (ExtendedFloatingActionButton) findViewById(R.id.expenseFab);
        ExtendedFloatingActionButton incFab = (ExtendedFloatingActionButton) findViewById(R.id.incomeFab);
        expFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#03DAC5")));
        incFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
        state = false;
        enableButtons(expenseOptions);
        disableButtons(incomeOptions);
    }

    public void disableButtons(String[] buttonNames) {
        for (String name : buttonNames) {
            int id = getResources().getIdentifier(name, "id", getPackageName());
            ExtendedFloatingActionButton button = (ExtendedFloatingActionButton) findViewById(id);
            button.setEnabled(false);
            button.hide();
        }
    }
    public void enableButtons(String[] buttonNames) {
        for (String name : buttonNames) {
            int id = getResources().getIdentifier(name, "id", getPackageName());
            ExtendedFloatingActionButton button = (ExtendedFloatingActionButton) findViewById(id);
            button.setEnabled(true);
            button.show();
        }
    }
}