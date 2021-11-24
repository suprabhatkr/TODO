package com.example.todoplanner.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todoplanner.AddNewTask;
import com.example.todoplanner.ListActivity;
import com.example.todoplanner.Model.ToDoModel;
import com.example.todoplanner.R;
import com.example.todoplanner.Utils.DatabaseHandler;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoModel> todoList;
    private ListActivity activity;
    private DatabaseHandler db;

    public ToDoAdapter(DatabaseHandler db,ListActivity activity){
        this.db = db;
        this.activity = activity;
    }

    public ToDoAdapter(ListActivity activity){
        this.activity= activity;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(ItemView);
    }
    public void onBindViewHolder(ViewHolder holder,int position){
        db.openDatabase();
        final ToDoModel item = todoList.get(position);
        holder.task.setText(item.getText());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(),1);
                }
                else{
                    db.updateStatus(item.getId(),0);
                }
            }
        });
    }

    public int getItemCount(){
        return todoList.size();
    }
    private boolean toBoolean(int x){
        return x!=0;
    }

    public void setTasks(List<ToDoModel> taskList){
        this.todoList=taskList;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void deleteItem(int position){
        ToDoModel item = todoList.get(position);
        db.deleteTask(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        ToDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getText());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(),AddNewTask.TAG);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.todoCheckbox);
        }
    }
}

