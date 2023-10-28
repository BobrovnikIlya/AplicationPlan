package com.example.planapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DayActivity extends AppCompatActivity {
    private String[] namesArr = new String[]{"Josh", "John", "George", "Bob", "Alex"};
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        AddActivity.sw = true;

        listView = (ListView) findViewById(R.id.listViews);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(DayActivity.this, R.layout.task_design, R.id.task_name, namesArr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(DayActivity.this, (String)listView.getItemAtPosition(i), Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goAdd(view);
            }
        });

    }
    public void goAdd(View v){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
    public void goDate(View v){
        MainActivity.numberFragment = 2;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
