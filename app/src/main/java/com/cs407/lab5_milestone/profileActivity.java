package com.cs407.lab5_milestone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public class profileActivity extends AppCompatActivity {
    static ArrayList<Notes> notes1 = new ArrayList<>();
    TextView welcomeMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        welcomeMessageTextView = findViewById(R.id.welcomeMessageTextView); // assuming you have a TextView to display the welcome message

        // Retrieve the username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFERENCES_NAME, MODE_PRIVATE);
        String username = sharedPreferences.getString(MainActivity.USERNAME_KEY, "");

        String welcomeMessage = "Welcome user " + username + " to the note app.";
        welcomeMessageTextView.setText(welcomeMessage);

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes1 = dbHelper.readNotes(username);

        ArrayList<String> displayNotes = new ArrayList<>();
        for(Notes notes: notes1){
            displayNotes.add(String.format("Title:%s\nDate:%s\n", notes.getTitle(), notes.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView notesListView = (ListView) findViewById(R.id.notesListView);
        notesListView.setAdapter(adapter);

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), noteWriting.class);
                intent.putExtra("noteId", i);
                startActivity(intent);
            }

        });

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.addnote) {
            Toast.makeText(this, "addnote selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, noteWriting.class);
            intent.putExtra("noteId", -1);
            startActivity(intent);
        } else if (itemId == R.id.logout) {
            Toast.makeText(this, "logout selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}