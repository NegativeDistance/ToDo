package com.petrus.todo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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

        arrayToDo = ListLoader.readList("toDo",this);
        arrayGroceries = ListLoader.readList("grocery",this);
        adapterToDo = new RecyclerViewAdapter(arrayToDo, this, this);
        adapterGrocery = new RecyclerViewAdapter(arrayGroceries, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        modeSelect(mode);

        buttonAdd.setOnClickListener(view ->
        {
            String taskItem = (editTextAddItem.getText().toString());

            if (taskItem.trim().isEmpty())
            {
                Toast.makeText(MainActivity.this, "Field can not be empty", Toast.LENGTH_LONG).show();
            }
            else
            {
                Task newItem = new Task(taskItem);

                arrayActive.add(newItem);
                editTextAddItem.setText(null);
                ListLoader.writeList(arrayActive, mode, getApplicationContext());
                getModeAdapter().notifyItemInserted(arrayActive.size() - 1);
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
        arrayActive.get(position).setComplete(!arrayActive.get(position).isComplete());
        ListLoader.writeList(arrayActive, mode, getApplicationContext());
        getModeAdapter().notifyItemChanged(position);
    }

    @Override
    public void onItemLongClick(int position)
    {
        AlertDialog.Builder alertDelete = new AlertDialog.Builder(MainActivity.this);
        alertDelete.setTitle(R.string.delete_item);
        alertDelete.setMessage("Do you want to delete this item?");
        alertDelete.setCancelable(false);
        alertDelete.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());
        alertDelete.setPositiveButton("Yes", (dialogInterface, i) ->
        {
            arrayActive.remove(position);
            ListLoader.writeList(arrayActive, mode, getApplicationContext());
            getModeAdapter().notifyItemRemoved(position);
        });

        AlertDialog alertDialog = alertDelete.create();
        alertDialog.show();
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

    public RecyclerViewAdapter getModeAdapter()
    {
        RecyclerViewAdapter modeAdapter = null;
        if (mode.equals("toDo"))
        {
            modeAdapter = adapterToDo;
        }
        else if (mode.equals("grocery"))
        {
            modeAdapter = adapterGrocery;
        }
        return  modeAdapter;
    }
}