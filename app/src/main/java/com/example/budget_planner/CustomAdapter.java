package com.example.budget_planner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList type, total, change, date;

    Animation translate_anim;

    CustomAdapter (Context context, ArrayList type, ArrayList total, ArrayList change, ArrayList date) {
        this.context = context;
        this.type = type;
        this.total = total;
        this.change = change;
        this.date = date;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.typeTxt.setText(String.valueOf(type.get(position)));
        holder.totalTxt.setText(String.valueOf(total.get(position)));
        holder.changeTxt.setText(String.valueOf(change.get(position)));
        holder.dateTxt.setText(String.valueOf(date.get(position)));
    }

    @Override
    public int getItemCount() {
        return type.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView typeTxt;
        TextView totalTxt;
        TextView changeTxt;
        TextView dateTxt;

        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTxt = itemView.findViewById(R.id.type);
            totalTxt = itemView.findViewById(R.id.total);
            changeTxt = itemView.findViewById(R.id.change);
            dateTxt = itemView.findViewById(R.id.date);
            mainLayout = itemView.findViewById(R.id.rowMainLayout);
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
