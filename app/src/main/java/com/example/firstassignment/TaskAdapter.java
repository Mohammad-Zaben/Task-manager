package com.example.firstassignment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(@NonNull Context context, ArrayList<Task> dataArrayList) {
        super(context, R.layout.list_item, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Task task = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView textViewItem = view.findViewById(R.id.textViewItem);
        TextView textViewSubItem = view.findViewById(R.id.textViewSubItem);
        CheckBox checkBox = view.findViewById(R.id.checkBox);


        textViewItem.setText(task.toString());

        int index = task.getPriorityIndex();

        if (index == 0)
            textViewSubItem.setText("Urgent and Important");
        else if (index == 1)
            textViewSubItem.setText("Not Urgent but Important");
        else if (index == 2)
            textViewSubItem.setText("Urgent but Not Important");
        else
            textViewSubItem.setText("Not Urgent and Not Important");


        if (task.isDoneOrDue()) {
            checkBox.setChecked(true);
            textViewSubItem.setTextColor(Color.parseColor("#008000"));
        } else {
            checkBox.setChecked(false);
            textViewSubItem.setTextColor(Color.RED);
        }
        return view;
    }
}



