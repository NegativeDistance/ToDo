package com.petrus.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface
{
    EditText editTextAddItem;
    TextView textViewTitle;
    Button buttonAdd;
    MaterialButton buttonToDo, buttonGrocery;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapterToDo;
    RecyclerViewAdapter adapterGrocery;
    ArrayList<Task> arrayToDo = new ArrayList<>();
    ArrayList<Task> arrayGroceries = new ArrayList<>();
    ArrayList<Task> arrayActive;
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
        recyclerView = findViewById(R.id.recyclerViewToDo);

        arrayToDo = ListLoader.readToDo(this);
        arrayGroceries = ListLoader.readGrocery(this);
        adapterToDo = new RecyclerViewAdapter(arrayToDo, this, this);
        adapterGrocery = new RecyclerViewAdapter(arrayGroceries, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        modeSelect(mode);

        buttonAdd.setOnClickListener(view ->
        {
            Task newItem = new Task(editTextAddItem.getText().toString());

            switch(mode)
            {
                case "toDo":
                    arrayToDo.add(newItem);
                    editTextAddItem.setText(null);
                    ListLoader.writeToDo(arrayToDo, getApplicationContext());
                    adapterToDo.notifyDataSetChanged();
                    break;

                case "grocery":
                    arrayGroceries.add(newItem);
                    editTextAddItem.setText(null);
                    ListLoader.writeGrocery(arrayGroceries, getApplicationContext());
                    adapterGrocery.notifyDataSetChanged();
                    break;
            }

        });

        buttonToDo.setOnClickListener(view ->
        {
            mode = "toDo";
            modeSelect(mode);
        });

        buttonGrocery.setOnClickListener(view ->
        {
            mode = "grocery";
            modeSelect(mode);
        });
    }

    @Override
    public void onItemClick(int position)
    {
        if (!arrayActive.get(position).isComplete())
        {
            arrayActive.get(position).setComplete(true);
        }
        else
        {
            arrayActive.get(position).setComplete(false);
        }
        ListLoader.writeToDo(arrayActive, getApplicationContext());
        adapterToDo.notifyDataSetChanged();
        adapterGrocery.notifyDataSetChanged();
    }

    public void modeSelect(String mode)
    {
        switch(mode)
        {
            case "toDo":
                textViewTitle.setText(R.string.app_name);
                buttonToDo.setIconTintResource(R.color.white);
                buttonGrocery.setIconTintResource(R.color.gray);
                recyclerView.setBackgroundColor(getColor(R.color.light_gray));
                recyclerView.setAdapter(adapterToDo);

                arrayActive = arrayToDo;
                break;

            case "grocery":
                textViewTitle.setText(R.string.groceries);
                buttonGrocery.setIconTintResource(R.color.white);
                buttonToDo.setIconTintResource(R.color.gray);
                recyclerView.setBackgroundColor(getColor(R.color.light_blue));
                recyclerView.setAdapter(adapterGrocery);

                arrayActive = arrayGroceries;
                break;
        }
    }
}