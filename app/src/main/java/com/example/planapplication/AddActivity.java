package com.example.planapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {
    EditText name_input, description_input, date_input;
    private String selectedDate;
    private CalendarView calendarView;
    private EditText nameEt, descriptionEt;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        nameEt = (EditText) findViewById(R.id.nameEt);
        descriptionEt = (EditText) findViewById(R.id.descriptionEt);

        Calendar calendar = Calendar.getInstance();
        selectedDate = calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH)+1) + "." + calendar.get(Calendar.YEAR);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "." + (month + 1) + "." + year;
            }
        });
    }

    public void Save(View v) {
        if(ProgressFragment.isChild){
            MyDataBaseHelper myDB = new MyDataBaseHelper(AddActivity.this);
            myDB.addChildTask(nameEt.getText().toString().trim(),
                    descriptionEt.getText().toString().trim(),
                    selectedDate);
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
        }else {
            MyDataBaseHelper myDB = new MyDataBaseHelper(AddActivity.this);
            myDB.addTask(nameEt.getText().toString().trim(),
                    descriptionEt.getText().toString().trim(),
                    selectedDate);
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    public void goBack(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}