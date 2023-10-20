package com.cs407.lab5_milestone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class noteWriting extends AppCompatActivity {
    private int noteId = -1;

    Button deleteButton;
    Button saveButton;
    EditText editTextText;

    Context context;
    SQLiteDatabase sqLiteDatabase;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_writing);

        // Initialization moved here:
        context = getApplicationContext();
        sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        dbHelper = new DBHelper(sqLiteDatabase);
        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        saveButton = findViewById(R.id.save);
        deleteButton = findViewById(R.id.delete);
        editTextText = findViewById(R.id.editTextText);
        Intent intent = getIntent();

        // Retrieve the data from the Intent (make sure the key matches what was used to put the data)
        int defaultValue = -1;  // example default value
        int receivedData = intent.getIntExtra("noteId", defaultValue);
        noteId = receivedData;

        if (noteId != -1){
            Notes notes = profileActivity.notes1.get(noteId);
            String noteContent = notes.getContent();
            editTextText.setText(noteContent);
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFERENCES_NAME, MODE_PRIVATE);
                String username = sharedPreferences.getString(MainActivity.USERNAME_KEY, "");
                String userText = editTextText.getText().toString();
                String title;
                DateFormat dateFormat = new SimpleDateFormat("MM/DD/YYYY HH:mm:ss");
                String date = dateFormat.format(new Date());
                Log.i("Info", "Printing noteid before using in condition" + noteId);
                if (noteId == -1) {
                    title = "NOTES_" + (profileActivity.notes1.size() + 1);
                    Log.i("info", "printing content to be saved " + userText);
                    dbHelper.saveNotes(username, title, date, userText);

                } else {
                    Log.i("Info", "Printing noteid from update update condition " + noteId);
                    title = "NOTES_" + (noteId + 1);
                    dbHelper.updateNotes(username, title, date, userText);
                }

                Intent intent = new Intent(noteWriting.this, profileActivity.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFERENCES_NAME, MODE_PRIVATE);
                String username = sharedPreferences.getString(MainActivity.USERNAME_KEY, "");
                String userText = editTextText.getText().toString();

                String title = "NOTES_" + (noteId+1);
                dbHelper.deleteNotes(userText, title);
                Intent intent = new Intent(noteWriting.this, profileActivity.class);
                startActivity(intent);
            }
        });

    }




}