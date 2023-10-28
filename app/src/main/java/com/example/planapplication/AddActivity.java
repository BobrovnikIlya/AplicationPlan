package com.example.planapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    public static boolean sw = false;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        calendarView = (CalendarView) findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(AddActivity.this, "Выбранная дата: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goBack(View v) {
        if(sw){
            Intent intent = new Intent(this, DayActivity.class);
            startActivity(intent);
        }else {
            MainActivity.numberFragment = 3;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}