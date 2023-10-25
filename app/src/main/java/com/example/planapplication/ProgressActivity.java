package com.example.planapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class ProgressActivity extends AppCompatActivity {
    private String[] namesArr = new String[]{"Josh", "John", "George", "Bob", "Alex"};
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        listView = (ListView) findViewById(R.id.gridView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.task_design, R.id.task_name, namesArr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ProgressActivity.this, (String)listView.getItemAtPosition(i), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void goHome(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void goProgress(View v){
        Intent intent = new Intent(this, ProgressActivity.class);
        startActivity(intent);
    }
    public void goDate(View v){
        Intent intent = new Intent(this, DateActivity.class);
        startActivity(intent);
    }
}