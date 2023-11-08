package com.example.planapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList nameTask, dateTask, idTask, descriptionTask;
    CustomAdapter(Context context, ArrayList nameTask, ArrayList dateTask, ArrayList idTask, ArrayList descriptionTask){
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("Custom adapter", "Загрузка данных в recycler");
        holder.name_txt.setText(String.valueOf(nameTask.get(position)));
        holder.date_txt.setText(String.valueOf(dateTask.get(position)));
        if(MainActivity.numberFragment == 3)
            holder.percent_txt.setText(String.valueOf(50+"%"));
        if(MainActivity.numberFragment == 2){
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
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date_txt = itemView.findViewById(R.id.date_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
            percent_txt = itemView.findViewById(R.id.percent_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
