package com.example.firstassignment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class Task {
    private String title;
    private boolean DoneOrDue;
    private String Descr;
    private Date currentDate;
    private String priority="UaI";
    Calendar calendar = Calendar.getInstance();
    private int priorityIndex;

    public Task(String title, String Descr,int priority){
        this.title=title;
        this.Descr=Descr;
        priorityIndex=priority;
        DoneOrDue=false; // by default is false, that mean any new task is already due
        currentDate=calendar.getTime();

        if(priority==0)
            this.priority="UaI";
        else if (priority==1)
            this.priority="nUbI";
        else if (priority==2)
            this.priority="UbnI";
        else
            this.priority="nUanI";


    }

    public void setDescr(String descr) {
        Descr = descr;
    }

    public void setDoneOrDue(boolean doneOrDue) {

        DoneOrDue = doneOrDue;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriorityIndex(int priorityIndex) {
        this.priorityIndex = priorityIndex;
    }

    public void setPriority(int priority) {
        if(priority==0)
            this.priority="UaI";
        else if (priority==1)
            this.priority="nUbI";
        else if (priority==2)
            this.priority="UbnI";
        else
            this.priority="nUanI";
        setPriorityIndex(priority);
    }

    public String getDescr() {
        return Descr;
    }

    public String getTitle() {return title;}

    public boolean isDoneOrDue() { return DoneOrDue;}


    public Date getCurrentDate() {
        return currentDate;
    }

    public String getPriority() {
        return priority;
    }

    public int getPriorityIndex() {
        return priorityIndex;
    }

    @Override
    public String toString() {
        return title;
    }
}
