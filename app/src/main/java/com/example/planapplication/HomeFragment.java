package com.example.planapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
    GridView gridView;
    private String[] namesArr = new String[]{"Josh", "John", "George", "Bob", "Alex"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, null);
        gridView = (GridView) v.findViewById(R.id.gridView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.task_design, R.id.task_name, namesArr);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), (String)gridView.getItemAtPosition(i), Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }
}