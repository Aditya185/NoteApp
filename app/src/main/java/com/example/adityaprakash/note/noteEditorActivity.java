package com.example.adityaprakash.note;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;

import static com.example.adityaprakash.note.MainActivity.notes;

public class noteEditorActivity extends AppCompatActivity {

    int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = findViewById(R.id.editText);

        Intent intent = getIntent();
         noteId = intent.getIntExtra("noteId",-1);

        if(noteId != -1){
            editText.setText(notes.get(noteId));

        }
        else{
            notes.add("");
            noteId = notes.size() -1;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              notes.set(noteId,String.valueOf(s));
              MainActivity.arrayAdapter.notifyDataSetChanged();
              //data storage code
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.adityaprakash.note", Context.MODE_PRIVATE);
                HashSet set = new HashSet<>(notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
