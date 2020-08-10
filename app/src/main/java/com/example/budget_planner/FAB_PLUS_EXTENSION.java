package com.example.budget_planner;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import android.os.Bundle;
import android.widget.EditText;

public class FAB_PLUS_EXTENSION extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_b__p_l_u_s__e_x_t_e_n_s_i_o_n);

    }
    public void onFabClick (View view){
        EditText inputMoney =(EditText) findViewById(R.id.inputMoney);
        MainActivity mainObj = new MainActivity();
        float tempBalance = Float.parseFloat(inputMoney.getText().toString());
        mainObj.setBalance(mainObj.getBalance()+tempBalance);
        finish();
    }
}