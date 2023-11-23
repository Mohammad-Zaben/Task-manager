package com.example.firstassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddNewTask extends AppCompatActivity {
private TextInputEditText title;
private SharedPreferences prefs;
private SharedPreferences.Editor editor;
private TextInputEditText descr;
private ArrayList list;
private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        title=findViewById(R.id.txtTitle1);
        descr=findViewById(R.id.txtDescr1);
        spinner=findViewById(R.id.spinner1);



       /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 'position' is the index of the selected item
                Toast.makeText(AddNewTask.this, "Selected index: " + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });*/

        setupSharedPrefs();
        GsonToList();
    }
    public void GsonToList(){
        Gson gson = new Gson();
        String str = prefs.getString("DATA", "");

        Type listType = new TypeToken<ArrayList<Task>>() {}.getType();
        list=gson.fromJson(str,listType);
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

    }
    public void btAddOnClick(View view){

        String Title = title.getText().toString();
        String Descr = descr.getText().toString();

        if(!Title.equals("")) {  // We can leave the description blank but the title cannot be left blank
            Task task = new Task(Title, Descr,spinner.getSelectedItemPosition());
            list.add(task);

            Gson gson = new Gson();
            String taskString = gson.toJson(list);
            editor.putString("DATA", taskString);
            editor.commit();
            title.setText("");
            descr.setText("");
            Toast.makeText(this, "The Task has been Added", Toast.LENGTH_SHORT).show();
        }


    }

}