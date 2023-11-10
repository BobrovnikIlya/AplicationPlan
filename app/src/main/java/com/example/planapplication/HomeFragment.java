package com.example.planapplication;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    private MyDataBaseHelper myDB;
    Cursor cursor;
    public boolean check = false;
    private Switch aSwitch;
    private CustomAdapter customAdapter;
    private ArrayList<String> nameTask, dateTask, idTask, descriptionTask, completeTask;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UpdateActivity.sw = false;
        View v = inflater.inflate(R.layout.fragment_home, null);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        aSwitch = (Switch) v.findViewById(R.id.aSwitch);

        myDB = new MyDataBaseHelper(getContext());

        nameTask = new ArrayList<>();
        dateTask = new ArrayList<>();
        idTask = new ArrayList<>();
        descriptionTask = new ArrayList<>();
        completeTask = new ArrayList<>();

        storeDateInArrays();

        if (aSwitch != null) {
            aSwitch.setOnCheckedChangeListener(this::onCheckedChanged);
        }

        return v;
    }
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        check = isChecked;
        storeDateInArrays();
    }
    public void storeDateInArrays(){
        idTask.clear();
        nameTask.clear();
        descriptionTask.clear();
        dateTask.clear();
        completeTask.clear();

        if(check){
            cursor = myDB.readCompleteHomeData();
        }else{
            cursor = myDB.readHomeData();
        }

        if (cursor.getCount() == 0){
            Toast.makeText(getActivity(), "Нет данных", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                idTask.add(cursor.getString(0));
                nameTask.add(cursor.getString(1));
                descriptionTask.add(cursor.getString(2));
                dateTask.add(cursor.getString(3));
                completeTask.add(cursor.getString(4));
            }
        }
        customAdapter = new CustomAdapter(HomeFragment.this, getContext(), nameTask, dateTask, idTask, descriptionTask);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}