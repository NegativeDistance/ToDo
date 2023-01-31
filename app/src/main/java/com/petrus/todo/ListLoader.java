package com.petrus.todo;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ListLoader
{
    public static final String FILENAME = "list_items.dat";

    public static void writeData (ArrayList<Task> toDo, Context context)
    {
        try
        {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(toDo);
            oos.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static ArrayList<Task> readData (Context context)
    {
        ArrayList<Task> toDo = null;

        try
        {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            toDo = (ArrayList<Task>) ois.readObject();
            ois.close();
        }
        catch (FileNotFoundException e)
        {
            toDo = new ArrayList<>();
            e.printStackTrace();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return toDo;
    }
}
