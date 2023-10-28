package com.example.planapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

public class CalendarFragment extends Fragment {
    CalendarView calendarView;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, null);
        calendarView = (CalendarView) v.findViewById(R.id.calendarV);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                goDay(view);
            }
        });
        return v;
    }
    public void goDay(View v){
        Intent intent = new Intent(getActivity(), DayActivity.class);
        startActivity(intent);
    }
}