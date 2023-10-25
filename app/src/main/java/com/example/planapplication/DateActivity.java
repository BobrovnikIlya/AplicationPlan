package com.example.planapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
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