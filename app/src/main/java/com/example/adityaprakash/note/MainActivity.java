package com.example.adityaprakash.note;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ArrayAdapter arrayAdapter;
    static  ArrayList<String> notes = new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.adityaprakash.note", Context.MODE_PRIVATE);
        HashSet<String> set =(HashSet<String>)sharedPreferences.getStringSet("notes",null);
        if(set==null){
            notes.add("Hello Aditya");
        }else{
            notes = new ArrayList<>(set);
        }
        ListView listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),noteEditorActivity.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                 final int itemtodelete = i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are You Sure?")
                        .setMessage("Do You Want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(itemtodelete);
                                arrayAdapter.notifyDataSetChanged();
                                //data storage code
                                HashSet set = new HashSet<>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes",set).apply();
                            }
                        })
                        .setNegativeButton("No",null).show();

                return true;
            }
        });

    }

    //MENU
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()== R.id.add_note){
             Intent intent = new Intent(getApplicationContext(),noteEditorActivity.class);
             startActivity(intent);
             return true;
         }
         return false;
    }
}
