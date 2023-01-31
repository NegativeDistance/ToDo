package com.petrus.todo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task implements Serializable
{
    private String description, addDate, finishDate;
    private boolean complete;

    public Task(String description)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");
        this.description = description;
        this.addDate = dtf.format(LocalDateTime.now());
        this.complete = false;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String task)
    {
        this.description = task;
    }

    public String getAddDate()
    {
        return addDate;
    }

    public void setAddDate(String addDate)
    {
        this.addDate = addDate;
    }

    public String getFinishDate()
    {
        return finishDate;
    }

    public void setFinishDate(String finishDate)
    {
        this.finishDate = finishDate;
    }

    public void setFinishDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a");
        this.finishDate = dtf.format(LocalDateTime.now());
    }

    public boolean isComplete()
    {
        return complete;
    }

    public void setComplete(boolean complete)
    {
        this.complete = complete;
        setFinishDate();
    }
}
