package com.example.planapplication;

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
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    private MyDataBaseHelper myDB;
    private CustomAdapter customAdapter;
    private ArrayList<String> nameTask, dateTask, idTask, descriptionTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, null);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        myDB = new MyDataBaseHelper(getContext());

        nameTask = new ArrayList<>();
        dateTask = new ArrayList<>();
        idTask = new ArrayList<>();
        descriptionTask = new ArrayList<>();

        storeDateInArrays();

        customAdapter = new CustomAdapter(getActivity(), nameTask, dateTask, idTask, descriptionTask);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }
    private void storeDateInArrays(){
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(getActivity(), "Нет данных", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                idTask.add(cursor.getString(0));
                nameTask.add(cursor.getString(1));
                descriptionTask.add(cursor.getString(2));
                dateTask.add(cursor.getString(3));
            }
        }
    }
}