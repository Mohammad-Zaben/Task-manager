package com.example.firstassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskActivity extends AppCompatActivity {

    private TextInputEditText title;
    private TextInputEditText descr;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Switch Do;

    private Button btDelete;
    private Button btSave;
    private ArrayList list;
    private Intent intent;
    private TextView txtDate;
    private Spinner spinner;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        title = findViewById(R.id.txtTitle1);
        descr = findViewById(R.id.txtDescr1);
        Do = findViewById(R.id.Do);
        btDelete = findViewById(R.id.btDelete);
        btSave = findViewById(R.id.btDSave);
        txtDate = findViewById(R.id.txtDate);
        spinner = findViewById(R.id.spinner2);
        intent = getIntent();
        index = (int) intent.getExtras().get("index");

        setupSharedPrefs();
        GsonToList(); // take list from SharedPreferences

        LoadData();//load data in the show

        spinner.setSelection((((Task) list.get(index)).getPriorityIndex()));

        spinner.setEnabled(false);

        title.setFocusable(false);
        title.setClickable(false);

        descr.setFocusable(false);
        descr.setClickable(false);


    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

    }

    public void GsonToList() {
        Gson gson = new Gson();
        String str = prefs.getString("DATA", "");

        Type listType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        list = gson.fromJson(str, listType);
    }

    public void EditOnClick(View view) {
        title.setFocusable(true);
        title.setClickable(true);
        title.setFocusableInTouchMode(true);
        descr.setFocusable(true);
        descr.setClickable(true);
        descr.setFocusableInTouchMode(true);
        spinner.setEnabled(true);

        btSave.setVisibility(View.VISIBLE);
    }

    public void SaveOnClock(View view) {
        ((Task) list.get(index)).setTitle(title.getText().toString());
        ((Task) list.get(index)).setDescr(descr.getText().toString());
        ((Task) list.get(index)).setPriority(spinner.getSelectedItemPosition());
        SaveData();

        title.setFocusable(false);
        title.setClickable(false);

        descr.setFocusable(false);
        descr.setClickable(false);

        spinner.setEnabled(false);

        btSave.setVisibility(View.GONE);
        Toast.makeText(TaskActivity.this, "The task has been modified", Toast.LENGTH_LONG).show();
    }

    public void DeleteOnClick(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete this task?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TaskActivity.this, "The Task has been Deleted", Toast.LENGTH_SHORT
                        ).show();
                        list.remove(index);
                        SaveData();
                        finish();

                    }
                }).setNegativeButton("No", null)
                .show();
    }


    public void SwitchOnClick(View view) {
        Switch sw = (Switch) view;
        if (sw.isChecked()) {
            ((Task) (list.get(index))).setDoneOrDue(true);
            SaveData();
        } else {
            ((Task) (list.get(index))).setDoneOrDue(false);
            SaveData();
        }
    }

    public void SaveData() {
        Gson gson = new Gson();
        String taskString = gson.toJson(list);
        editor.putString("DATA", taskString);
        editor.commit();
    }

    public void LoadData() {//to load data in the show
        Date date = (((Task) list.get(index)).getCurrentDate());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy , HH:mm");
        String date1 = dateFormat.format(date);
        String[] d = date1.split(",");

        txtDate.setText("  written on " + d[0] + "at" + d[1]);
        title.setText((((Task) list.get(index)).getTitle()));
        descr.setText((((Task) list.get(index)).getDescr()));

        if (((Task) (list.get(index))).isDoneOrDue()) {
            Do.setChecked(true);
        }
    }

}