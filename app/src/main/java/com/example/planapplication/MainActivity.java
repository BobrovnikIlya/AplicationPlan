package com.example.planapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String[] namesArr = new String[]{"Josh", "John", "George", "Bob", "Alex"};
    public static int numberFragment = 1;
    public static String day = "";
    private GridView gridView;

    HomeFragment homeFrag = new HomeFragment();
    CalendarFragment calendarFragment = new CalendarFragment();
    ProgressFragment progressFragment = new ProgressFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switch (numberFragment){
            case 1: setNewFragment(homeFrag); break;
            case 2: setNewFragment(calendarFragment); break;
            case 3: setNewFragment(progressFragment); break;
        }

    }
    public void goHome(View v){
        MainActivity.numberFragment = 1;
        setNewFragment(homeFrag);
    }
    public void goProgress(View v){
        MainActivity.numberFragment = 3;
        setNewFragment(progressFragment);
    }
    public void goDate(View v){
        MainActivity.numberFragment = 2;
        setNewFragment(calendarFragment);
    }
    private void setNewFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}