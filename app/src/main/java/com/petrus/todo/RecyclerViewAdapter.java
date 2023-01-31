package com.petrus.todo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.MyViewHolder>
{
    Context context;
    ArrayList<String> toDoList;

    public RecyclerViewAdapter(ArrayList<String> toDoList, Context context)
    {
        this.toDoList = toDoList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        //Where we inflate the layout (give look to rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_list_item, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapter.MyViewHolder holder, int position)
    {
        //assigning values to the views based on item position
        holder.itemToDo.setText(toDoList.get(position));
    }

    @Override
    public int getItemCount()
    {
        //number of items to be displayed
        return toDoList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        //Grabbing the views from our recycler_list_item file

        TextView itemToDo;
        CheckBox checkBox;

        public MyViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);

            itemToDo = itemView.findViewById(R.id.textViewItemToDo);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
