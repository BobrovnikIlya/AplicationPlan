package com.example.planapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String[] namesArr = new String[]{"Josh", "John", "George", "Bob", "Alex"};
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.task_design, R.id.task_name, namesArr);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, (String)gridView.getItemAtPosition(i), Toast.LENGTH_LONG).show();
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