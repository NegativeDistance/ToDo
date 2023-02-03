package com.petrus.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface
{
    EditText editTextAddItem;
    TextView textViewTitle;
    Button buttonAdd;
    MaterialButton buttonToDo, buttonGrocery;
    RecyclerView recyclerViewToDo;
    ArrayList<Task> arrayToDo = new ArrayList<>();
    ArrayList<Task> arrayGroceries = new ArrayList<>();
    RecyclerViewAdapter adapter;
    SharedPreferences sharedPreferences;
    String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("Preferences", MODE_PRIVATE);
        mode = sharedPreferences.getString("mode", "toDo");

        editTextAddItem = findViewById(R.id.editTextAddItem);
        textViewTitle = findViewById(R.id.textViewTitle);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonToDo = findViewById(R.id.buttonToDo);
        buttonGrocery = findViewById(R.id.buttonGrocery);
        recyclerViewToDo = findViewById(R.id.recyclerViewToDo);

        arrayToDo = ListLoader.readToDo(this);
        arrayGroceries = ListLoader.readGrocery(this);

        adapter = new RecyclerViewAdapter(arrayToDo, this, this);

        recyclerViewToDo.setAdapter(adapter);
        recyclerViewToDo.setLayoutManager(new LinearLayoutManager(this));

        modeSelect(mode);

        buttonAdd.setOnClickListener(view ->
        {
            Task newItem = new Task(editTextAddItem.getText().toString());
            arrayToDo.add(newItem);
            editTextAddItem.setText(null);
            ListLoader.writeToDo(arrayToDo, getApplicationContext());
            adapter.notifyDataSetChanged();
        });

        buttonToDo.setOnClickListener(view ->
        {
            modeSelect("toDo");
        });

        buttonGrocery.setOnClickListener(view ->
        {
            modeSelect("grocery");
        });
    }

    @Override
    public void onItemClick(int position)
    {
        if (!arrayToDo.get(position).isComplete())
        {
            arrayToDo.get(position).setComplete(true);
        }
        else
        {
            arrayToDo.get(position).setComplete(false);
        }
        ListLoader.writeToDo(arrayToDo, getApplicationContext());
        adapter.notifyDataSetChanged();
    }

    public void modeSelect(String mode)
    {
        switch(mode)
        {
            case "toDo":
                textViewTitle.setText(R.string.app_name);
                buttonToDo.setIconTintResource(R.color.white);
                buttonGrocery.setIconTintResource(R.color.gray);
                recyclerViewToDo.setBackgroundColor(getColor(R.color.light_gray));
                break;

            case "grocery":
                textViewTitle.setText(R.string.groceries);
                buttonGrocery.setIconTintResource(R.color.white);
                buttonToDo.setIconTintResource(R.color.gray);
                recyclerViewToDo.setBackgroundColor(getColor(R.color.light_blue));
                break;
        }
    }
}