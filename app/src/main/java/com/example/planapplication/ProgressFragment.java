package com.example.planapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Output;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProgressFragment extends Fragment{
    private Button buttonAdd;
    private RecyclerView recyclerView;
    private MyDataBaseHelper myDB;
    private CustomAdapter customAdapter;
    private ArrayList<String> nameTask, dateTask, idTask, descriptionTask;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UpdateActivity.sw = false;
        View v = inflater.inflate(R.layout.fragment_progress, null);

        buttonAdd = (Button) v.findViewById(R.id.buttonAdd);
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

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });
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