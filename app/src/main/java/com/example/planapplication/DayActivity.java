package com.example.planapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DayActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private MyDataBaseHelper myDB;
    private CustomAdapter customAdapter;
    private ArrayList<String> nameTask, dateTask, idTask, descriptionTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        UpdateActivity.sw = true;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        myDB = new MyDataBaseHelper(DayActivity.this);

        nameTask = new ArrayList<>();
        dateTask = new ArrayList<>();
        idTask = new ArrayList<>();
        descriptionTask = new ArrayList<>();

        storeDateInArrays();

        customAdapter = new CustomAdapter(DayActivity.this,this, nameTask, dateTask, idTask, descriptionTask);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DayActivity.this));

    }
    private void storeDateInArrays(){
        Cursor cursor = myDB.readDataInDay(MainActivity.day);
        if (cursor.getCount() == 0){
            Toast.makeText(DayActivity.this, "Нет данных", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                idTask.add(cursor.getString(0));
                nameTask.add(cursor.getString(1));
                descriptionTask.add(cursor.getString(2));
                dateTask.add(cursor.getString(3));
            }
        }
    }
    public void goDate(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
