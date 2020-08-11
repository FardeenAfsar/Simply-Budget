package com.example.budget_planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class FAB_PLUS_EXTENSION extends AppCompatActivity {
    private boolean state = true;
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
    }
    public void expenseFab (View view){
        ExtendedFloatingActionButton expFab = (ExtendedFloatingActionButton) findViewById(R.id.expenseFab);
        ExtendedFloatingActionButton incFab = (ExtendedFloatingActionButton) findViewById(R.id.incomeFab);
        expFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#03DAC5")));
        incFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
        state = false;
    }
}