package com.example.budget_planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FAB_PLUS_EXTENSION extends AppCompatActivity {
    private boolean state = true;
    private int storedPrevState = R.id.income_opt5;
    private String activeBtn = "Other";
    private String currentDate;
    private String[] incomeOptions = {"income_opt1", "income_opt2", "income_opt3", "income_opt4", "income_opt5"};
    private String[] expenseOptions = {"expense_opt1", "expense_opt2", "expense_opt3", "expense_opt4", "expense_opt5", "expense_opt6", "expense_opt7"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_b__p_l_u_s__e_x_t_e_n_s_i_o_n);

        //Set Transaction Time
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YY");
        currentDate = simpleDateFormat.format(new Date());
    }

    public void onFabClick (View view){
        MainActivity mainObj = new MainActivity();
        EditText inputMoney =(EditText) findViewById(R.id.inputMoney);
        if (!inputMoney.getText().toString().isEmpty()) {
            float tempBalance = Float.parseFloat(inputMoney.getText().toString());
            mainObj.setBalance((state) ? mainObj.getBalance() + tempBalance : mainObj.getBalance() - tempBalance);
        }
        finish();

        //MODEL
        History history;
        Database database = new Database(FAB_PLUS_EXTENSION.this);
        try {
            Float tempChange = state?Float.parseFloat(inputMoney.getText().toString()):-Float.parseFloat(inputMoney.getText().toString());
            if (tempChange != 0) {
                history = new History(mainObj.getBalance(), tempChange, activeBtn, currentDate);
                database.addOne(history);
            }
        }catch (Exception e){
            Toast.makeText(FAB_PLUS_EXTENSION.this, "Invalid Input",Toast.LENGTH_SHORT).show();
        }

    }
    public void incomeFab (View view){
        ExtendedFloatingActionButton incFab = (ExtendedFloatingActionButton) findViewById(R.id.incomeFab);
        ExtendedFloatingActionButton expFab = (ExtendedFloatingActionButton) findViewById(R.id.expenseFab);
        incFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#03DAC5")));
        expFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
        enableButtons(incomeOptions);
        disableButtons(expenseOptions);
    }
    public void expenseFab (View view){
        ExtendedFloatingActionButton expFab = (ExtendedFloatingActionButton) findViewById(R.id.expenseFab);
        ExtendedFloatingActionButton incFab = (ExtendedFloatingActionButton) findViewById(R.id.incomeFab);
        expFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#03DAC5")));
        incFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
        enableButtons(expenseOptions);
        disableButtons(incomeOptions);
    }
    public void optionsBtn (View view){
        deactivateButton(storedPrevState);
        switch (view.getId()) {
            case R.id.income_opt1:
                activeBtn = "Allowance";
                activateButton(R.id.income_opt1);
                state = true;
                break;
            case R.id.income_opt2:
                activeBtn = "Business Profits";
                activateButton(R.id.income_opt2);
                state = true;
                break;
            case R.id.income_opt3:
                activeBtn = "Salary";
                activateButton(R.id.income_opt3);
                state = true;
                break;
            case R.id.income_opt4:
                activeBtn = "Bonus";
                activateButton(R.id.income_opt4);
                state = true;
                break;
            case R.id.income_opt5:
                activeBtn = "Other";
                activateButton(R.id.income_opt5);
                state = true;
                break;
            case R.id.expense_opt1:
                activeBtn = "Food";
                activateButton(R.id.expense_opt1);
                state = false;
                break;
            case R.id.expense_opt2:
                activeBtn = "Transportation";
                activateButton(R.id.expense_opt2);
                state = false;
                break;
            case R.id.expense_opt3:
                activeBtn = "Social Life";
                activateButton(R.id.expense_opt3);
                state = false;
                break;
            case R.id.expense_opt4:
                activeBtn = "Bills";
                activateButton(R.id.expense_opt4);
                state = false;
                break;
            case R.id.expense_opt5:
                activeBtn = "Health";
                activateButton(R.id.expense_opt5);
                state = false;
                break;
            case R.id.expense_opt6:
                activeBtn = "Gifts";
                activateButton(R.id.expense_opt6);
                state = false;
                break;
            case R.id.expense_opt7:
                activeBtn = "Other";
                activateButton(R.id.expense_opt7);
                state = false;
                break;
        }
    }

    public void deactivateButton(int id) {
        ExtendedFloatingActionButton switchbtn = (ExtendedFloatingActionButton) findViewById(id);
        switchbtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
        switchbtn.setTextColor(Color.parseColor("#000000"));
    }
    public void activateButton(int id) {
        ExtendedFloatingActionButton switchbtn = (ExtendedFloatingActionButton) findViewById(id);
        switchbtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6200EE")));
        switchbtn.setTextColor(Color.parseColor("#FFFFFF"));
        storedPrevState = id;
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