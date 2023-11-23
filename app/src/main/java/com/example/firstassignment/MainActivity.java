package com.example.firstassignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstassignment.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private ArrayList<Task> list=new ArrayList<Task>();
private ListView listView ;
private SharedPreferences prefs;
private SharedPreferences.Editor editor;
public static final String DATA="DATA";
private TaskAdapter adapter;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Task t1 = new Task("play yoga","go to the park and play yoga at 10:30",0);
        Task t2 = new Task("clean the care","go to the garage and clean the car",1);
        Task t3 = new Task("shopping with my wife","ll my wife and go to met her in the mol",3);
        list.add(t1);
        list.add(t2);
        list.add(t3);
        setupSharedPrefs();
        SaveData();
        LoadData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LoadData();
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    public void SaveData(){
        Gson gson = new Gson();
        String str= prefs.getString("DATA","");
        if(str.equals(""))
        {
            String taskString = gson.toJson(list);
            editor.putString(DATA,taskString);
            editor.commit();
        }
    }

   public void LoadData(){
        Gson gson = new Gson();
        String str = prefs.getString("DATA", "");

        Type listType = new TypeToken<ArrayList<Task>>() {}.getType();
        ArrayList<Task> taskList = gson.fromJson(str, listType);
       ArrayAdapter<Task> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        adapter=new TaskAdapter(MainActivity.this,taskList);

        listView =findViewById(R.id.list_item);
        listView.setAdapter(adapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               Intent intent = new Intent(MainActivity.this, TaskActivity.class);
               intent.putExtra("index", (int)id);
               startActivity(intent);
           }
       });
    }
}