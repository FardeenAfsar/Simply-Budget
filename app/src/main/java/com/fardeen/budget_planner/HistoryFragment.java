package com.fardeen.budget_planner;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryFragment extends Fragment {
    Database database;
    private ArrayList<String> historyType, historyTotal, historyChange, historyDate;
    CustomAdapter customAdapter;
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = v.findViewById(R.id.recyclerview);

        database = new Database(getActivity());
        historyType = new ArrayList<>();
        historyTotal = new ArrayList<>();
        historyChange = new ArrayList<>();
        historyDate = new ArrayList<>();

        //recycler view
        displayData();
        customAdapter = new CustomAdapter(getActivity(), historyType, historyTotal, historyChange, historyDate);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }
    public void  displayData () {
        Cursor cursor = database.readData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(),"No History", Toast.LENGTH_SHORT).show();
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
