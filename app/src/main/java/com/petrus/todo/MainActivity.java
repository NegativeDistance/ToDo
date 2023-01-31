package com.petrus.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    EditText editTextAddItem;
    Button buttonAdd;
    RecyclerView recyclerViewToDo;
    ArrayList<String> arrayToDo = new ArrayList<>();
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAddItem = findViewById(R.id.editTextAddItem);
        buttonAdd = findViewById(R.id.buttonAdd);
        recyclerViewToDo = findViewById(R.id.recyclerViewToDo);

        arrayToDo = ListLoader.readData(this);
        adapter = new RecyclerViewAdapter(arrayToDo, this);

        recyclerViewToDo.setAdapter(adapter);
        recyclerViewToDo.setLayoutManager(new LinearLayoutManager(this));

        buttonAdd.setOnClickListener(view ->
        {
            String newItem = editTextAddItem.getText().toString();
            arrayToDo.add(newItem);
            editTextAddItem.setText(null);
            ListLoader.writeData(arrayToDo, getApplicationContext());
            adapter.notifyDataSetChanged();
        });
    }
}