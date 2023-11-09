package com.example.planapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {
    public static boolean sw = false;
    private EditText name_input, description_input;
    private String id, name, description, dateTask;
    private CalendarView calendarView;
    MyDataBaseHelper myDB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        name_input = (EditText) findViewById(R.id.name_input);
        description_input = (EditText) findViewById(R.id.description_input);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        try {
            getAndSetIntentData();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dateTask = dayOfMonth + "." + (month + 1) + "." + year;
            }
        });
    }
    private void getAndSetIntentData() throws ParseException {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("description") && getIntent().hasExtra("date")){
            //get data
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            description = getIntent().getStringExtra("description");
            dateTask = getIntent().getStringExtra("date");
            //set data
            name_input.setText(name);
            description_input.setText(description);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            Date date = sdf.parse(dateTask);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            calendarView.setDate(calendar.getTimeInMillis());
        }else{
            Toast.makeText(this, "Нет данных", Toast.LENGTH_SHORT).show();
        }
    }
    public void save(View v) {
        name = name_input.getText().toString().trim();
        description = description_input.getText().toString().trim();

        if(sw){
            myDB = new MyDataBaseHelper(UpdateActivity.this);
            myDB.updateTask(id, name, description, dateTask);
            Intent intent = new Intent(UpdateActivity.this, DayActivity.class);
            startActivity(intent);
        }else {
            myDB = new MyDataBaseHelper(UpdateActivity.this);
            myDB.updateTask(id, name, description, dateTask);
            Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    public void delete(View v) {
        if(sw){
            confirmDialog();
        }else {
            Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    public void goBack(View v) {
        if(sw){
            Intent intent = new Intent(this, DayActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    void confirmDialog(){
        AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить " +name+" ?");
        builder.setMessage("Вы уверены, что хотите удалить "+name+" ?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDB = new MyDataBaseHelper(UpdateActivity.this);
                myDB.deleteTask(id);
                Intent intent = new Intent(UpdateActivity.this, DayActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }
}