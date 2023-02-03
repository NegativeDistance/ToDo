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
    public static final String TODOFILE = "list_items.dat";
    public static final String GROCERYFILE = "grocery_items.dat";

    public static void writeList (ArrayList<Task> toDo, String mode, Context context)
    {
        String fileName = null;
        switch(mode)
        {
            case "toDo":
                fileName = TODOFILE;
                break;
            case "grocery":
                fileName = GROCERYFILE;
                break;
        }
        try
        {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(toDo);
            oos.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static ArrayList<Task> readList (String mode, Context context)
    {
        ArrayList<Task> openedList = null;

        String fileName = null;
        switch(mode)
        {
            case "toDo":
                fileName = TODOFILE;
                break;
            case "grocery":
                fileName = GROCERYFILE;
                break;
        }

        try
        {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            openedList = (ArrayList<Task>) ois.readObject();
            ois.close();
        }
        catch (FileNotFoundException e)
        {
            openedList = new ArrayList<>();
            e.printStackTrace();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return openedList;
    }
}
