package com.petrus.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Task> arrayListOfItems;

    public RecyclerViewAdapter(ArrayList<Task> toDoList, Context context, RecyclerViewInterface recyclerViewInterface)
    {
        this.arrayListOfItems = toDoList;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        //Where we inflate the layout (give look to rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_list_item, parent, false);

        return new RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapter.MyViewHolder holder, int position)
    {
        boolean complete = arrayListOfItems.get(position).isComplete();
        String completed = holder.itemView.getContext().getString(R.string.date_completed);

        //assigning values to the views based on item position
        holder.itemToDo.setText(arrayListOfItems.get(position).getDescription());
        holder.added.setText(arrayListOfItems.get(position).getAddDate());

        if (complete)
        {
            holder.completed.setText(String.format("%s%s", completed, arrayListOfItems.get(position).getFinishDate()));
            holder.itemToDo.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.itemToDo.setTextColor(Color.parseColor("#7C7C7C"));
            holder.added.setTextColor(Color.parseColor("#7C7C7C"));
            holder.checkBox.setChecked(true);
        }
        else if (!complete)
        {
            holder.completed.setText(null);
            holder.itemToDo.setPaintFlags(0);
            holder.itemToDo.setTextColor(Color.parseColor("#FF000000"));
            holder.added.setTextColor(Color.parseColor("#FF3388DD"));
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount()
    {
        //number of items to be displayed
        return arrayListOfItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        //Grabbing the views from our recycler_list_item file

        TextView itemToDo, added, completed;
        CheckBox checkBox;

        public MyViewHolder(@NonNull @NotNull View itemView, RecyclerViewInterface recyclerViewInterface)
        {
            super(itemView);

            itemToDo = itemView.findViewById(R.id.textViewItemToDo);
            checkBox = itemView.findViewById(R.id.checkBox);
            added = itemView.findViewById(R.id.textViewAdded);
            completed = itemView.findViewById(R.id.textViewCompleted);

            checkBox.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(recyclerViewInterface != null)
                    {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION)
                        {
                            itemToDo.setPaintFlags(itemToDo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View view)
                {
                    if(recyclerViewInterface != null)
                    {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION)
                        {
                            recyclerViewInterface.onItemLongClick(pos);
                        }
                    }
                    return true;
                }
            });
        }
    }
}
