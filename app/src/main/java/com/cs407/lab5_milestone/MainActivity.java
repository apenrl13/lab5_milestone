package com.cs407.lab5_milestone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String PREFERENCES_NAME = "user_prefs";
    public static final String USERNAME_KEY = "username";

    Button loginButton;
    EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login);
        usernameEditText = findViewById(R.id.usernameEditText); // assuming you have an EditText with this id for the username

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void login() {
        String username = usernameEditText.getText().toString().trim();

        // Store the username in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_KEY, username);
        editor.apply();

        Intent intent = new Intent(this, profileActivity.class);
        startActivity(intent);
    }


}