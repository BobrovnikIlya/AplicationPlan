package com.example.planapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ProgressFragment progressFragment;
    private HomeFragment homeFragment;
    private Activity activity;
    private Cursor cursor;
    private ArrayList nameTask, dateTask, idTask, descriptionTask, completeTask;
    CustomAdapter(Activity activity, Context context, ArrayList nameTask, ArrayList dateTask, ArrayList idTask, ArrayList descriptionTask){
        this.activity = activity;
        this.context = context;
        this.nameTask = nameTask;
        this.dateTask = dateTask;
        this.descriptionTask = descriptionTask;
        this.idTask = idTask;
        Log.d("Custom adapter", "Конструктор");
    }
    CustomAdapter(ProgressFragment fragment, Context context, ArrayList nameTask, ArrayList dateTask, ArrayList idTask, ArrayList descriptionTask){
        this.progressFragment = fragment;
        this.context = context;
        this.nameTask = nameTask;
        this.dateTask = dateTask;
        this.descriptionTask = descriptionTask;
        this.idTask = idTask;
        Log.d("Custom adapter", "Конструктор");
    }
    CustomAdapter(HomeFragment fragment, Context context, ArrayList nameTask, ArrayList dateTask, ArrayList idTask, ArrayList descriptionTask){
        this.homeFragment = fragment;
        this.context = context;
        this.nameTask = nameTask;
        this.dateTask = dateTask;
        this.descriptionTask = descriptionTask;
        this.idTask = idTask;
        Log.d("Custom adapter", "Конструктор");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);;
        switch (MainActivity.numberFragment){
            case 1:
                view = inflater.inflate(R.layout.task_design, parent, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.task_design2, parent, false);
                break;
            case 3:
                view = inflater.inflate(R.layout.my_row, parent, false);
                break;
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name_txt.setText(String.valueOf(nameTask.get(position)));
        holder.date_txt.setText(String.valueOf(dateTask.get(position)));
        if(homeFragment.check){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }

        if(MainActivity.numberFragment == 3) {
            holder.percent_txt.setText(String.valueOf(50 + "%")); //Просто чтобы с процентами работать
            holder.mainLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("id", String.valueOf(idTask.get(position)));
                    intent.putExtra("name", String.valueOf(nameTask.get(position)));
                    intent.putExtra("description", String.valueOf(descriptionTask.get(position)));
                    intent.putExtra("date", String.valueOf(dateTask.get(position)));
                    context.startActivity(intent);
                    return false;
                }
            });
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProgressFragment.isChild = true;
                    ProgressFragment.idBasic = idTask.get(position).toString();
                    Log.d("Adapter", "idBAsic =" + ProgressFragment.idBasic);
                    if(progressFragment != null)
                        progressFragment.storeDateInArrays();
                }
            });
        }else{
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) v;
                    MyDataBaseHelper myDB = new MyDataBaseHelper(context);
                    if(checkBox.isChecked()){
                        Log.d("Check", "Нажат");
                        myDB.updateTask(idTask.get(position).toString(), 1);
                    }else{
                        Log.d("Check", "Не нажат");
                        myDB.updateTask(idTask.get(position).toString(), 0);
                    }
                    homeFragment.storeDateInArrays();
                }
            });
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("id", String.valueOf(idTask.get(position)));
                    intent.putExtra("name", String.valueOf(nameTask.get(position)));
                    intent.putExtra("description", String.valueOf(descriptionTask.get(position)));
                    intent.putExtra("date", String.valueOf(dateTask.get(position)));
                    context.startActivity(intent);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return idTask.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date_txt, name_txt, percent_txt;
        CheckBox checkBox;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.complete_check);
            date_txt = itemView.findViewById(R.id.date_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
            percent_txt = itemView.findViewById(R.id.percent_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
