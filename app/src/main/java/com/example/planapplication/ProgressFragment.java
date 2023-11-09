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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProgressFragment extends Fragment{
    public static boolean isChild = false;
    public static String idBasic;
    private Button buttonAdd;
    private LinearLayout linearLayout;
    private Cursor cursor;
    private RecyclerView recyclerView;
    private MyDataBaseHelper myDB;
    private CustomAdapter customAdapter;
    private ArrayList<String> nameTask, dateTask, idTask, descriptionTask, completeTask;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UpdateActivity.sw = false;
        View v = inflater.inflate(R.layout.fragment_progress, null);

        Log.d("Создание прогресса", "запуск");

        buttonAdd = (Button) v.findViewById(R.id.buttonAdd);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        linearLayout = (LinearLayout) v.findViewById(R.id.linearLayout);

        myDB = new MyDataBaseHelper(getContext());

        nameTask = new ArrayList<>();
        dateTask = new ArrayList<>();
        idTask = new ArrayList<>();
        descriptionTask = new ArrayList<>();
        completeTask = new ArrayList<>();

        storeDateInArrays();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    public void storeDateInArrays(){
        idTask.clear();
        nameTask.clear();
        descriptionTask.clear();
        dateTask.clear();
        completeTask.clear();
        Log.d("StoreData", "Очистка");
        if(!isChild){
            cursor = myDB.readProgressDataBasic();
            Log.d("StoreData", "Basic");
        }
        else {
            cursor = myDB.readProgressDataChild(idBasic);
            Log.d("StoreData", "chield and Basic = " + idBasic);
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
            Log.d("StoreData", "Заполнение");
        }
        if(!isChild){
            customAdapter = new CustomAdapter(this, getContext(), nameTask, dateTask, idTask, descriptionTask);
        }else{
            customAdapter = new CustomAdapter(getActivity(), getContext(), nameTask, dateTask, idTask, descriptionTask);
        }
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}