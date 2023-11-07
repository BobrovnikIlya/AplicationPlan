package com.example.planapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Output;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProgressFragment extends Fragment{
    Button buttonAdd;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> taskList;
    private DatabaseHelper databaseHelper;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AddActivity.sw = false;
        View v = inflater.inflate(R.layout.fragment_progress, null);

        buttonAdd = (Button) v.findViewById(R.id.buttonAdd);
        listView = (ListView) v.findViewById(R.id.listView);

        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), R.layout.task_design, taskList);
        listView.setAdapter(adapter);

        databaseHelper = new DatabaseHelper(getActivity());
        taskList.addAll(databaseHelper.getAllTasks());
        adapter.notifyDataSetChanged();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}